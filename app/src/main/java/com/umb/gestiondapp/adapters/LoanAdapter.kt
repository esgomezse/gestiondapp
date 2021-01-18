package com.umb.gestiondapp.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.umb.gestiondapp.LoanActivity.Companion.BORROWED
import com.umb.gestiondapp.LoanActivity.Companion.NEW
import com.umb.gestiondapp.R
import com.umb.gestiondapp.models.LoanModel
import kotlinx.android.synthetic.main.item_loan.view.*


class LoanAdapter : RecyclerView.Adapter<LoanAdapter.LoanViewHolder>() {

    private var listLoan = listOf<LoanModel>()
    val events = MutableLiveData<LoanModel>()

    inner class LoanViewHolder(v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanAdapter.LoanViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false)
        return LoanViewHolder(view)
    }

    override fun getItemCount() = listLoan.count()

    override fun onBindViewHolder(holder: LoanAdapter.LoanViewHolder, position: Int) {
        val itemLoan = listLoan[position]
        //holder.itemView.imageInventario
        with(holder.itemView) {
            imvStudentId.setImageBitmap(setImage(itemLoan.image.encoded))
            txvStudentName.text = itemLoan.studname
            txvStudentCode.text = itemLoan.code
            txvStudentHelp.text = itemLoan.loan
            txvStudentClass.text = itemLoan.subject
            txvStudentGroup.text = itemLoan.group
            txvStudentDate.text = itemLoan.date
            setStatus(itemLoan.status, this)
            btnLendProduct.setOnClickListener {
                when (itemLoan.status){
                    NEW -> itemLoan.status = BORROWED
                    BORROWED -> itemLoan.status = ""
                }
                events.value = itemLoan
            }
        }
    }

    private fun setStatus(status: String, view: View) {
        when (status) {
            NEW -> {
                view.btnLendProduct.text = view.context.getString(R.string.lend)
            }
            BORROWED -> {
                view.btnLendProduct.text = view.context.getString(R.string.returned)
            }
            else -> {
                view.btnLendProduct.isVisible = false
            }
        }
    }

    private fun setImage(encoded: String): Bitmap? {
        val base64Image: String = encoded.split(",").get(1)
        val decodedString: ByteArray = Base64.decode(base64Image, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun setList(list: List<LoanModel>) {
        listLoan = list
        notifyDataSetChanged()
    }
}