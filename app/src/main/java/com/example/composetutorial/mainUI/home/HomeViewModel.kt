package com.example.composetutorial.mainUI.home

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetutorial.model.BottomItem
import com.example.composetutorial.model.PDFFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.emptyList
import androidx.core.net.toUri
import com.example.composetutorial.helper.SortType

class HomeViewModel : ViewModel() {
    private val _selectedTab = MutableStateFlow(HomeState())
    val selectedTab : StateFlow<HomeState> = _selectedTab
    private val _pdfLists = MutableStateFlow<List<PDFFile>>(emptyList())
    val pdfLists : StateFlow<List<PDFFile>> = _pdfLists

    private val _recentFiles = MutableStateFlow<List<PDFFile>>(emptyList())
    val recentFiles : StateFlow<List<PDFFile>> = _recentFiles

    fun handleIntent(intent: HomeIntent) {
        when(intent) {
            is HomeIntent.AllTabClicked -> {
                _selectedTab.value = _selectedTab.value.copy(selectedTab = "All")
            }
            is HomeIntent.StarredTabClicked -> {
                _selectedTab.value = _selectedTab.value.copy(selectedTab = "Starred")
            }
            is HomeIntent.SetVerticalClicked -> {
                _selectedTab.value = _selectedTab.value.copy(setVertical = true)
            }
            is HomeIntent.SetHorizontalClicked -> {
                _selectedTab.value = _selectedTab.value.copy(setVertical = false)
            }
            is HomeIntent.SetVerticalStarClicked -> {
                _selectedTab.value = _selectedTab.value.copy(setVerticalStar = true)
            }
            is HomeIntent.SetHorizontalStarClicked -> {
                _selectedTab.value = _selectedTab.value.copy(setVerticalStar = false)
            }
            is HomeIntent.ToggleStar -> {
                _pdfLists.value = _pdfLists.value.map {
                    if (it == intent.pdfFile) {
                        val update = it.copy(isStarred = !it.isStarred)
                        addToRecent(update)
                        update
                    } else it
                }
            }
            is HomeIntent.SetShowBottom -> {
                _selectedTab.value = _selectedTab.value.copy(
                    showBottom = !_selectedTab.value.showBottom,
                    selectedPDF = intent.pdfFile
                )
            }
            is HomeIntent.SetShowSecondBottom -> {
                _selectedTab.value = _selectedTab.value.copy(
                    showSecondBottom = !_selectedTab.value.showSecondBottom,
                    selectedPDF = intent.pdfFile
                )
            }
            is HomeIntent.SetShowRenameDialog -> {
                _selectedTab.value = selectedTab.value.copy(
                    showBottom = false,
                    showSecondBottom = false,
                    showRenameDialog = !_selectedTab.value.showRenameDialog,
                    selectedPDF = intent.pdfFile
                )
            }
            is HomeIntent.SetShowDeleteDialog -> {
                _selectedTab.value = selectedTab.value.copy(
                    showBottom = false,
                    showSecondBottom = false,
                    showDeleteDialog = !_selectedTab.value.showDeleteDialog,
                    selectedPDF = intent.pdfFile
                )
            }
            is HomeIntent.SetShowSortBottom -> {
                _selectedTab.value = _selectedTab.value.copy(
                    showSortBottom = !_selectedTab.value.showSortBottom
                )
            }
            is HomeIntent.SelectedSort -> {
                _selectedTab.value = _selectedTab.value.copy(selectedSort = intent.sort)
            }
        }
    }


    fun loadPDFFiles(context : Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val files = getPDFFiles(context)
            withContext(Dispatchers.Main) {
                _pdfLists.value = files
            }
        }
    }

    fun renamePDF(context: Context, pdfFile: PDFFile, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uri = pdfFile.path.toUri()

                val values = android.content.ContentValues().apply {
                    put(
                        MediaStore.Files.FileColumns.DISPLAY_NAME,
                        if(newName.endsWith(".pdf")) {
                            newName
                        } else {
                            "$newName.pdf"
                        }
                    )
                }
                context.contentResolver.update(
                    uri,
                    values,
                    null,
                    null

                )

                val starredIds = _pdfLists.value.filter{it.isStarred}.map{it.id}
                val newList= getPDFFiles(context)
                val updateList = newList.map {
                    pdf -> if(starredIds.contains(pdf.id)) {
                        pdf.copy(isStarred = true)
                    } else pdf
                }

                withContext(Dispatchers.Main) {
                    _pdfLists.value = updateList

                    val renamedFile = updateList.find{
                        it.id == pdfFile.id
                    }

                    renamedFile?.let {
                        addToRecent(it)
                    }
                }
            } catch (e: Exception) {
                Log.e("RenamePDF", "Rename error", e)
            }
        }
    }
    fun deletePDF(context: Context, pdfFile: PDFFile) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val uri = pdfFile.path.toUri()

                context.contentResolver.delete(
                    uri,
                    null,
                    null
                )

                val files = getPDFFiles(context)

                withContext(Dispatchers.Main) {
                    _pdfLists.value = files
                    deleteFromRecent(pdfFile)
                }
            } catch (e: Exception) {
                Log.e("Delete PDF", "Delete error", e)
            }
        }
    }
    fun sortPDF(sortType: SortType) {
        _pdfLists.value = when (sortType) {
            SortType.DATE_NEW_TO_OLD -> {
                _pdfLists.value.sortedByDescending {
                    it.date
                }
            }

            SortType.DATE_OLD_TO_NEW -> {
                _pdfLists.value.sortedBy {
                    it.date
                }
            }
            SortType.NAME_AZ -> {
                _pdfLists.value.sortedBy {
                    it.text.lowercase()
                }
            }

            SortType.NAME_ZA -> {
                pdfLists.value.sortedByDescending {
                    it.text.lowercase()
                }
            }
            SortType.FILE_SIZE_LARGE_TO_SMALL -> {
                pdfLists.value.sortedByDescending {
                    it.fileSize
                }
            }

            SortType.FILE_SIZE_SMALL_TO_LARGE -> {
                pdfLists.value.sortedBy {
                    it.fileSize
                }
            }
        }
    }

    fun addToRecent(pdf: PDFFile) {
        _recentFiles.value = listOf(pdf) + _recentFiles.value.filter { it.id != pdf.id }
    }

    fun deleteFromRecent(pdf: PDFFile) {
        _recentFiles.value = recentFiles.value.filter{it.id != pdf.id}
    }
}