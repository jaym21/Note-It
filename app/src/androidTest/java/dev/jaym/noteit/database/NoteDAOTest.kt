
package dev.jaym.noteit.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.jaym.noteit.R
import dev.jaym.noteit.data.Note
import dev.jaym.noteit.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class NoteDAOTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NoteDatabase
    private lateinit var dao: NoteDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NoteDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getNoteDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNote() = runBlockingTest {
        val color = R.color.colorPrimary
        val note = Note("abc", "ABC",  color)
        dao.insert(note)

        val allNotes = dao.getAllNotes().getOrAwaitValue()

        assertThat(allNotes).contains(note)
    }
}