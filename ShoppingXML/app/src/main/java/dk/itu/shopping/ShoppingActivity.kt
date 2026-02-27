package dk.itu.shopping

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class ShoppingActivity : AppCompatActivity() {
    //Shopping V6 using ViewModel, Live Data and RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shoppingmain)
        val fm = supportFragmentManager
        val fragmentUI = fm.findFragmentById(R.id.container_ui_landscape)
        val fragmentList = fm.findFragmentById(R.id.container_list)
        setUpFragments(fragmentUI, fragmentList)
    }

    private fun setUpFragments(fragmentUI: Fragment?, fragmentList: Fragment?) {
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (fragmentUI == null && fragmentList == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.container_ui_landscape, UIFragment())
                    .add(R.id.container_list, ListFragment())
                    .commit()
            }
        } else {
            //Orientation portrait
            if (fragmentUI == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.main_fragment, UIFragment())
                    .commit()
            }
        }
    }
}