package com.example.noteup.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.noteup.R
import com.example.noteup.components.NoteButton
import com.example.noteup.components.NoteInputText
import com.example.noteup.model.Note
import com.example.noteup.util.formatDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
            ){

    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column {
        TopAppBar(title = {
                          Text(text = stringResource(id = R.string.app_name))
                          
        },
            actions = {
                Icon(imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Icon")
                      },
                backgroundColor = Color(0xFF012752)
        )
//        CONTENT
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            NoteInputText(modifier = Modifier.padding(
                top = 9.dp, bottom = 8.dp
            ),
                text = title,
                label = "Title",
                onTextChange = {
                    if (it.all { char ->
                        char.isDefined()
                    }) title = it
                })

            NoteInputText(Modifier.padding(
                top = 9.dp, bottom = 9.dp
            ),
                text = description,
                label = "Add a Note",
                onTextChange = {
                    if (it.all { char ->
                            char.isDefined()
                        }) description = it
                })

            NoteButton(text = "save",
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()){
                        onAddNote(Note(title = title, description = description))
                        title = ""
                        description = ""
                        Toast.makeText(context, "Note Added",
                        Toast.LENGTH_SHORT).show()
                    }
                })
        }
        Divider(modifier = Modifier.padding(10.dp))
        LazyColumn {
            items(notes){note ->
                NoteRow(note = note,
                    onNoteClicked = {
                        onRemoveNote(note)
                        Toast.makeText(context, "Note Deleted",
                            Toast.LENGTH_SHORT).show()
                    })
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit) {
    Surface(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 25.dp, bottomStart = 25.dp))
            .fillMaxWidth(),
        color = Color(0xFF012752),
        elevation = 6.dp) {
        Column(modifier
            .clickable {onNoteClicked(note) }
            .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {
            
            Text(text = note.title,
                    style = MaterialTheme.typography.subtitle2)
            
            Text(text = note.description,
                style = MaterialTheme.typography.subtitle1)

            Spacer(modifier = Modifier.padding(1.dp))
            Text(text = formatDate(note.entryDate.time),
                style = MaterialTheme.typography.overline)
            Spacer(modifier = Modifier.padding(2.dp))
        }
    }
}
