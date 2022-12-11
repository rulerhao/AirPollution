package com.rulhouse.airpollution.view

import com.rulhouse.airpollution.R
import com.rulhouse.airpollution.base.BaseAdapter
import com.rulhouse.airpollution.databinding.MiddleVerticalCardBinding
import com.rulhouse.airpollution.model.remote.air_pollution.dto.Record

class MiddleCardAdapter (
    list: List<Record>,
    private val middleCardListener: MiddleCardListener
): BaseAdapter<MiddleVerticalCardBinding, Record>(list) {

    override val layoutId: Int = R.layout.middle_vertical_card

    override fun bind(binding: MiddleVerticalCardBinding, item: Record) {
        binding.apply {
            record = item
            listener = middleCardListener
            executePendingBindings()
        }
    }

}

interface MiddleCardListener {
    fun onDetailClicked(record: Record)
}