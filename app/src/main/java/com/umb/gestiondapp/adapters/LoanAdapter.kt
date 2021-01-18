package com.umb.gestiondapp.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umb.gestiondapp.R
import com.umb.gestiondapp.models.LoanModel
import kotlinx.android.synthetic.main.item_loan.view.*


class LoanAdapter: RecyclerView.Adapter<LoanAdapter.LoanViewHolder> (){

    private var listLoan = listOf<LoanModel>()

    inner class LoanViewHolder(v : View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanAdapter.LoanViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_loan, parent, false)
        return LoanViewHolder(view)
    }

    override fun getItemCount() = listLoan.count()

    override fun onBindViewHolder(holder: LoanAdapter.LoanViewHolder, position: Int) {
        val itemLoan = listLoan[position]
        //holder.itemView.imageInventario
        with(holder.itemView){
            imvStudentId.setImageBitmap(setImage(itemLoan.image.encoded))
            txvStudentName.text = itemLoan.studname
            txvStudentCode.text = itemLoan.code
            txvStudentHelp.text = itemLoan.loan
            txvStudentClass.text = itemLoan.signature
            txvStudentGroup.text = itemLoan.group
            txvStudentDate.text = itemLoan.date
        }
    }

    private fun setImage(encoded: String): Bitmap? {
        val base64Image: String = encoded.split(",").get(1)
        val decodedString: ByteArray = Base64.decode(base64Image, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    }

    fun setList(list: List<LoanModel>){
        listLoan = list
        notifyDataSetChanged()
    }
}