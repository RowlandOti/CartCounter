package com.rowland.cartcounter.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.rowland.cartcounter.view.CartCounterActionView

class MainActivity : AppCompatActivity() {

    private var cartCount: Int = 0;

    private var countTextView: TextView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countTextView = findViewById<TextView>(R.id.count_number)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val itemData = menu.findItem(R.id.action_addcart)
        val actionView = itemData.actionView as CartCounterActionView
        actionView.setItemData(menu, itemData)
        actionView.setCount(cartCount)
        return super.onPrepareOptionsMenu(menu)
    }

    // Do actions based on selected menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_addcart -> {
                ++cartCount
                CartCounterActionView.setCountStep(this, 1)
                countTextView!!.setText(cartCount.toString())
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
