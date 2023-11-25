package com.example.shoppingcart

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), CartRVAdapter.CartItemClickInterface {

    lateinit var itemsRV: RecyclerView
    lateinit var addFAB: FloatingActionButton

    lateinit var list: List<CartItems>
    lateinit var cartRVAdapter: CartRVAdapter
    lateinit var cartViewModal: CartViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        itemsRV = findViewById(R.id.idRVItems)
        addFAB = findViewById(R.id.idFABAdd)

        list = ArrayList<CartItems>()
        cartRVAdapter = CartRVAdapter(list,this)
        itemsRV.layoutManager = LinearLayoutManager(this)
        itemsRV.adapter = cartRVAdapter
        val cartRepository = CartRepository(CartDatabase(this))
        val factory = CartViewModelFactory(cartRepository)
        cartViewModal = ViewModelProvider(this, factory).get(CartViewModel::class.java)
        cartViewModal.allCartItems().observe(this, Observer {
            cartRVAdapter.list = it
            cartRVAdapter.notifyDataSetChanged()
        })

        addFAB.setOnClickListener{
            openDialog()
        }

    }

    fun openDialog(){

        val dialog = Dialog(this)

        dialog.setContentView(R.layout.cart_add_dialog)

        val cancelBtn = dialog.findViewById<Button>(R.id.idBtnCancel)
        val addBtn = dialog.findViewById<Button>(R.id.idBtnAdd)
        val itemEdt = dialog.findViewById<EditText>(R.id.idEditItemName)
        val itemPriceEdt = dialog.findViewById<EditText>(R.id.idEditItemPrice)
        val itemQuantityEdt = dialog.findViewById<EditText>(R.id.idEditItemQuantity)

        cancelBtn.setOnClickListener{
            dialog.dismiss()
        }

        addBtn.setOnClickListener{
            val itemName : String = itemEdt.text.toString()
            val itemPrice : String = itemPriceEdt.text.toString()
            val itemQuantity : String = itemQuantityEdt.text.toString()

            val qty : Int = itemQuantity.toInt()
            val pr : Int = itemPrice.toInt()

            if(itemName.isNotEmpty() && itemPrice.isNotEmpty() && itemQuantity.isNotEmpty()){
                val items = CartItems(itemName, qty, pr)
                cartViewModal.insert(items)
                Toast.makeText(applicationContext, "Item add to cart successfully", Toast.LENGTH_SHORT).show()
                cartRVAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }else{
                Toast.makeText(applicationContext, "Fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()

    }

    override fun onItemClick(cartItems: CartItems) {
        cartViewModal.delete(cartItems)
        cartRVAdapter.notifyDataSetChanged()
        Toast.makeText(applicationContext, "...deleted....", Toast.LENGTH_SHORT).show()
    }
}