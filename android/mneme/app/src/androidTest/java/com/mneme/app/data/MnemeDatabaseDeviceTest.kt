package com.mneme.app.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.room.Room
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Callable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.runBlocking

@RunWith(AndroidJUnit4::class)
class MnemeDatabaseDeviceTest {
    @Test
    fun openingDatabaseEnablesSecureDelete() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val database = MnemeDatabase.get(context).openHelper.writableDatabase
        database.query("PRAGMA secure_delete").use { cursor ->
            assertTrue(cursor.moveToFirst())
            assertEquals(1, cursor.getInt(0))
        }
    }

    @Test
    fun concurrentDatabaseUseCanOpenAnotherEncryptedConnection() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val database = MnemeDatabase.get(context)
        val sqlite = database.openHelper.writableDatabase
        val firstConnectionAcquired = CountDownLatch(1)
        val releaseFirstConnection = CountDownLatch(1)
        val executor = Executors.newFixedThreadPool(2)
        val firstUse = executor.submit {
            sqlite.beginTransaction()
            try {
                firstConnectionAcquired.countDown()
                check(releaseFirstConnection.await(5, TimeUnit.SECONDS))
            } finally {
                sqlite.endTransaction()
            }
        }

        try {
            assertTrue(firstConnectionAcquired.await(5, TimeUnit.SECONDS))
            val secondUse = executor.submit(Callable {
                sqlite.query("SELECT * FROM follow_through_suggestions").use { cursor ->
                    cursor.count
                }
            })
            assertEquals("The read should use another SQLCipher connection", 0, secondUse.get(5, TimeUnit.SECONDS))
        } finally {
            releaseFirstConnection.countDown()
            executor.shutdown()
        }
        firstUse.get(5, TimeUnit.SECONDS)
    }

    @Test
    fun staleCompletionSuggestionDoesNotChangeTaskOrClaimSuccess() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val database = Room.inMemoryDatabaseBuilder(context, MnemeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        try {
            val staleNoteId = database.notes().insert(
                NoteEntity(
                    title = "Send slides",
                    body = "Send the presentation slides",
                    source = "synthetic test",
                    contentHash = "stale-completion-test",
                    cardType = CardType.PROMISE.storage,
                    cardStatus = CardStatus.DONE.storage,
                ),
            )
            val suggestionId = database.followThrough().insert(
                FollowThroughSuggestion(
                    commitmentId = staleNoteId,
                    evidenceSource = "synthetic test",
                    evidenceText = "I sent the slides",
                    evidenceHash = "stale-completion-evidence",
                    confidence = 0.9f,
                ),
            )

            assertNull(database.followThrough().acceptAndComplete(suggestionId, 10_000L))
            assertEquals(CardStatus.DONE, database.notes().getById(staleNoteId)?.status())
            assertEquals(FollowThroughSuggestion.State.PENDING, database.followThrough().pendingById(suggestionId)?.state)
        } finally {
            database.close()
        }
    }
}
