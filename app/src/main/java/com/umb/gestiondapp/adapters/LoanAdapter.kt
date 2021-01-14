package com.umb.gestiondapp.adapters

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
            txvStudentName.text = itemLoan.studname
            txvStudentCode.text = itemLoan.code
            txvStudentHelp.text = itemLoan.loan
            txvStudentClass.text = itemLoan.signature
            txvStudentGroup.text = itemLoan.group
        }
    }
    fun setList(list: List<LoanModel>){
        listLoan = list
    }
}