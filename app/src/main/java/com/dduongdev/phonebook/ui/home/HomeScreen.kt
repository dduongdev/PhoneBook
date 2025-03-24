package com.dduongdev.phonebook.ui.home

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dduongdev.phonebook.PhoneBookTopAppBar
import com.dduongdev.phonebook.R
import com.dduongdev.phonebook.data.Contact
import com.dduongdev.phonebook.ui.AppViewModelProvider
import com.dduongdev.phonebook.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToContactEntry: () -> Unit,
    navigateToContactEdit: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val uiState = viewModel.uiState
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { PhoneBookTopAppBar(title = stringResource(HomeDestination.titleRes), canNavigateBack = false) },
        floatingActionButton = { AddContactFloatingButton(onClick = navigateToContactEntry) }
    ) { innerPadding ->
        ContactList(
            contacts = uiState.contacts,
            onItemUpdate = navigateToContactEdit,
            onItemDelete = { contact -> coroutineScope.launch { viewModel.deleteContact(contact) } },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun ContactItem(
    contact: Contact,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    var isContextMenuVisible by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.padding_small))
            .padding(horizontal = dimensionResource(R.dimen.padding_medium))
            .onSizeChanged { itemHeight = with(density) { it.height.toDp() } }
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        isContextMenuVisible = true
                        pressOffset = DpOffset(it.x.toDp(), it.y.toDp() - itemHeight)
                    },

                    onTap = {
                        context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.phoneNumber}")))
                    }
                )
            },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        ContactRow(contact)
        ContactDropdownMenu(
            isVisible = isContextMenuVisible,
            onDismiss = { isContextMenuVisible = false },
            pressOffset = pressOffset,
            onUpdate = onUpdate,
            onDelete = onDelete
        )
    }
}

@Composable
fun ContactRow(contact: Contact) {
    Row(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactAvatar(contact.name)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = contact.name, style = TextStyle(fontSize = 20.sp))
        Spacer(modifier = Modifier.weight(1f))
        Text(text = contact.phoneNumber, style = TextStyle(fontSize = 16.sp))
    }
}

@Composable
fun ContactAvatar(name: String) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(Color.Gray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = name.first().uppercase(), style = TextStyle(fontSize = 24.sp), color = Color.White)
    }
}

@Composable
fun ContactDropdownMenu(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    pressOffset: DpOffset,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {
    DropdownMenu(expanded = isVisible, onDismissRequest = onDismiss, offset = pressOffset) {
        DropdownMenuItem(onClick = {
            onDismiss()
            onUpdate()
        }, text = { Text(stringResource(R.string.edit)) })
        DropdownMenuItem(onClick = {
            onDismiss()
            onDelete()
        }, text = { Text(stringResource(R.string.delete)) })
    }
}

@Composable
fun AddContactFloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape),
        containerColor = Color.DarkGray
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = stringResource(R.string.add_contact_button),
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun ContactList(
    contacts: List<Contact>,
    onItemUpdate: (Int) -> Unit,
    onItemDelete: (Contact) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        /**
         * Trong [LazyColumn], mỗi item được hiển thị thông qua items(). Khi cuộn danh sách hoặc thay đổi
         * dữ liệu, Compose có thể tái sử dụng UI của một item thay vì tạo mới để tối ưu hiệu suất.
         * Ví dụ, khi xóa contact số 3, thì contact số 4 sẽ đè lên contact số 3, thay đổi về dữ liệu nhưng vẫn
         * giữ các trạng thái khác, ví dụ như Intent đến số điện thoại thứ 3.
         * Thêm [key] vào items sẽ giúp giữ đúng trạng thái của item khi danh sách thay đổi, tránh lỗi UI
         * khi thao tác trên danh sách động, cải thiện hiệu suất vì không cần vẽ lại toàn bộ danh sách.
         */
        items(items = contacts, key = { it.id }) { contact ->
            ContactItem(
                contact = contact,
                onUpdate = { onItemUpdate(contact.id) },
                onDelete = { onItemDelete(contact) }
            )
        }
    }
}
