package com.flux.store.helper

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.io.File
import java.util.Locale

object ComposeFileHelper {
    // 1. Single file picker launcher
    @Composable
    fun RememberSingleFilePicker(onResult: (Uri?) -> Unit) {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent(),
            onResult = onResult
        )
        LaunchedEffect(Unit) {}
    }
    // 2. Multiple file picker launcher
    @Composable
    fun RememberMultipleFilesPicker(onResult: (List<Uri>) -> Unit) {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetMultipleContents(),
            onResult = onResult
        )
        LaunchedEffect(Unit) {}
    }
    // 3. Show file name
    @Composable
    fun FileNameText(uri: Uri?) {
        val context = LocalContext.current
        val name = uri?.let { ComposeFileHelper.getFileName(context, it) } ?: ""
        Text(text = name)
    }
    // 4. Show file size
    @Composable
    fun FileSizeText(uri: Uri?) {
        val context = LocalContext.current
        val size = uri?.let { ComposeFileHelper.getFileSize(context, it) } ?: 0L
        Text(text = ComposeFileHelper.formatFileSize(size))
    }
    // 5. Preview image
    @Composable
    fun ImagePreview(uri: Uri, modifier: Modifier = Modifier) {
        AsyncImage(model = uri, contentDescription = null, modifier = modifier)
    }
    // 6. Button to open file picker
    @Composable
    fun FilePickerButton(label: String = "Pick File", onPick: () -> Unit) {
        Button(onClick = onPick) { Text(label) }
    }
    // 7. Download progress bar
    @Composable
    fun FileDownloadProgress(progress: Float) {
        LinearProgressIndicator(progress = progress, modifier = Modifier.fillMaxWidth())
    }
    // 8. Share file intent
    @Composable
    fun ShareFileButton(uri: Uri, label: String = "Share File") {
        val context = LocalContext.current
        Button(onClick = {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = context.contentResolver.getType(uri)
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            context.startActivity(Intent.createChooser(intent, null))
        }) { Text(label) }
    }
    // 9. Delete file action
    fun deleteFile(uri: Uri, context: Context): Boolean {
        return try {
            context.contentResolver.delete(uri, null, null) > 0
        } catch (_: Exception) { false }
    }
    // 10. Get file name
    fun getFileName(context: Context, uri: Uri): String {
        var name = ""
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val idx = cursor.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
            if (cursor.moveToFirst()) name = cursor.getString(idx)
        }
        return name
    }
    // 11. Get file size
    fun getFileSize(context: Context, uri: Uri): Long {
        var size: Long = 0
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val idx = cursor.getColumnIndex(android.provider.OpenableColumns.SIZE)
            if (cursor.moveToFirst()) size = cursor.getLong(idx)
        }
        return size
    }
    // 12. Format file size
    fun formatFileSize(size: Long): String {
        val kb = size / 1024.0
        val mb = kb / 1024.0
        return when {
            mb >= 1 -> String.format("%.2f MB", mb)
            kb >= 1 -> String.format("%.2f KB", kb)
            else -> "$size B"
        }
    }
    // 13. Open file with external app
    fun openFile(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, context.contentResolver.getType(uri))
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, null))
    }
    // 14. Read text from Uri
    suspend fun readText(context: Context, uri: Uri): String =
        context.contentResolver.openInputStream(uri)?.bufferedReader()
            ?.use { it.readText() } ?: ""
    // 15. Write text to file Uri
    suspend fun writeText(context: Context, uri: Uri, text: String) {
        context.contentResolver.openOutputStream(uri)?.bufferedWriter()
            ?.use { it.write(text) }
    }
    // 16. Read bytes from Uri
    suspend fun readBytes(context: Context, uri: Uri): ByteArray =
        context.contentResolver.openInputStream(uri)?.use { it.readBytes() } ?: ByteArray(0)
    // 17. Write bytes to Uri
    suspend fun writeBytes(context: Context, uri: Uri, data: ByteArray) {
        context.contentResolver.openOutputStream(uri)?.use { it.write(data) }
    }
    // 18. Remember read permission
    @Composable
    fun RememberReadPermission(onResult: (Boolean) -> Unit) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
            onResult
        )
        LaunchedEffect(Unit) {}
    }
    // 19. Remember manage external storage permission
    @Composable
    fun RememberManageAllFilesPermission(onResult: (Boolean) -> Unit) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission(),
            onResult
        )
        LaunchedEffect(Unit) {}
    }
    // 20. File chooser dialog component
    @Composable
    fun FileChooserDialog(isOpen: Boolean, onClose: () -> Unit, onSelect: (Uri) -> Unit) {
        // TODO: implement a dialog with file list
    }
    // 21. Get MIME type
    fun getMimeType(context: Context, uri: Uri): String? =
        context.contentResolver.getType(uri)
    // 22. Get file extension
    fun getExtension(uri: Uri): String? =
        uri.path?.substringAfterLast('.', missingDelimiterValue = "")
    // 23. Zip multiple Uris (placeholder)
    fun zipFiles(context: Context, uris: List<Uri>, outUri: Uri) {
        // TODO
    }
    // 24. Unzip file (placeholder)
    fun unzipFile(context: Context, zipUri: Uri, destDir: File) {
        // TODO
    }
    // 25. Remember last picked Uri state
    @Composable
    fun rememberLastPickedUri(): MutableState<Uri?> =
        remember { mutableStateOf(null) }
    // 26. Remember last picked Uris state
    @Composable
    fun rememberLastPickedUris(): MutableState<List<Uri>> =
        remember { mutableStateOf(emptyList()) }
    // 27. Show loading placeholder
    @Composable
    fun FileLoadingPlaceholder() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
        ) { /* TODO */ }
    }
    // 28. File action buttons row
    @Composable
    fun FileActionRow(onOpen: () -> Unit, onShare: () -> Unit, onDelete: () -> Unit) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = onOpen) { Text("Open") }
            Button(onClick = onShare) { Text("Share") }
            Button(onClick = onDelete) { Text("Delete") }
        }
    }
    // 29. Show file URI text
    @Composable
    fun FileUriText(uri: Uri?) { Text(text = uri.toString()) }
    // 30. FilePickerScreen scaffold (placeholder)
    @Composable
    fun FilePickerScreen() { /* TODO */ }
    // 31. File upload progress state
    @Composable
    fun rememberUploadProgress(): MutableState<Float> = remember { mutableStateOf(0f) }
    // 32. File download progress state
    @Composable
    fun rememberDownloadProgress(): MutableState<Float> = remember { mutableStateOf(0f) }
    // 33. FileInfo Card
    @Composable
    fun FileInfoCard(uri: Uri) {
        Column(modifier = Modifier.padding(8.dp)) {
            FileNameText(uri)
            FileSizeText(uri)
        }
    }
    // 34. Clear cache directory
    fun clearCacheDir(context: Context) {
        context.cacheDir.listFiles()?.forEach { it.deleteRecursively() }
    }
    // 35. Save bitmap from Uri (placeholder)
    fun saveBitmap(context: Context, uri: Uri, target: File) {
        // TODO
    }
    // 36. Load bitmap as ImageBitmap (placeholder)
    fun loadBitmap(uri: Uri): androidx.compose.ui.graphics.ImageBitmap {
        // TODO
        throw NotImplementedError()
    }
    // 37. rememberFilePickerContentDescription
    @Composable
    fun rememberFilePickerContentDescription(): String = remember { "Select a file" }
    // 38. Read JSON from assets
    @Composable
    fun readJsonAsset(context: Context, assetName: String): String {
        return context.assets.open(assetName).bufferedReader().use { it.readText() }
    }
    // 39. Write to temp cache file
    fun writeTempFile(context: Context, name: String, data: ByteArray): File {
        val file = File(context.cacheDir, name)
        file.writeBytes(data)
        return file
    }
    // 40. Remember last error message state
    @Composable
    fun rememberLastError(): MutableState<String?> = remember { mutableStateOf(null) }
    // 41. Record file access event (placeholder)
    fun recordFileAccess(uri: Uri) { /* TODO */ }
    // 42. Validate file size limit
    fun isFileWithinLimit(context: Context, uri: Uri, maxBytes: Long): Boolean {
        return getFileSize(context, uri) <= maxBytes
    }
    // 43. File type icon (placeholder)
    @Composable
    fun FileTypeIcon(uri: Uri) { /* TODO */ }
    // 44. FileDragAndDropArea (placeholder)
    @Composable
    fun FileDragAndDropArea(onDrop: (Uri) -> Unit) { /* TODO */ }
    // 45. rememberFileNameEditable
    @Composable
    fun rememberFileNameEditable(initial: String = ""): MutableState<String> = remember { mutableStateOf(initial) }
    // 46. Validate file extension
    fun isValidExtension(uri: Uri, allowed: List<String>): Boolean {
        val ext = getExtension(uri) ?: return false
        return allowed.contains(ext.lowercase(Locale.getDefault()))
    }
    // 47. Show file permission rationale dialog (placeholder)
    @Composable
    fun FilePermissionRationale(onDismiss: () -> Unit) { /* TODO */ }
    // 48. rememberFilePickerMimeType
    @Composable
    fun rememberFilePickerMimeType(): String = remember { "*/*" }
    // 49. launchFilePicker
    fun launchFilePicker(mimeType: String = "*/*", launcher: androidx.activity.result.ActivityResultLauncher<String>) {
        launcher.launch(mimeType)
    }
    // 50. Render file preview based on MIME (placeholder)
    @Composable
    fun FilePreview(uri: Uri) { /* TODO */ }
}