package dk.itu.shopping

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoppingViewModel : ViewModel() {

    private val itemsDB: ItemsDB = ItemsDB.get()

    val uiState: MutableLiveData<ShoppingUiState> =
        MutableLiveData<ShoppingUiState>(ShoppingUiState(itemsDB.items, itemsDB.size()))

    fun onAddItemClick(newWhat: TextView, newWhere: TextView, activity: FragmentActivity) {
        val whatS = newWhat.text.toString().trim { it <= ' ' }
        val whereS = newWhere.text.toString().trim { it <= ' ' }
        if (whatS.isNotEmpty() && whereS.isNotEmpty()) {
            itemsDB.addItem(whatS, whereS)
            newWhat.text = ""
            newWhere.text = ""
            newWhat.onEditorAction(EditorInfo.IME_ACTION_DONE)
            newWhere.onEditorAction(EditorInfo.IME_ACTION_DONE)
            uiState.value = ShoppingUiState(itemsDB.items, itemsDB.size())
        } else showToast(activity)
    }

    fun onDeleteItemClick(what: String, where: String, activity: FragmentActivity) {
        val message = if (uiState.value!!.itemList.any { it.what == what && it.where == where }) {
            itemsDB.removeItem(what, where)
            uiState.value = ShoppingUiState(itemsDB.items, itemsDB.size())
            "Removed $what from $where"
        } else {
            "$what from $where not found"
        }
        showToast(activity, message)
    }

    private fun showToast(activity: FragmentActivity) {
        Toast.makeText(activity, R.string.empty_toast, Toast.LENGTH_LONG).show()
    }

    private fun showToast(activity: FragmentActivity, message: CharSequence) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    data class ShoppingUiState(
        val itemList: ArrayList<Item>,
        val itemListSize: Int
    )
}