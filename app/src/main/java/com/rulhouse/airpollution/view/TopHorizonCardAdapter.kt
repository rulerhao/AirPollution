package com.rulhouse.airpollution.view

import com.rulhouse.airpollution.R
import com.rulhouse.airpollution.base.BaseAdapter
import com.rulhouse.airpollution.databinding.TopHorizonCardBinding
import com.rulhouse.airpollution.model.remote.air_pollution.dto.Record

class TopHorizonCardAdapter (
    list: List<Record>,
): BaseAdapter<TopHorizonCardBinding, Record>(list) {

    override val layoutId: Int = R.layout.top_horizon_card

    override fun bind(binding: TopHorizonCardBinding, item: Record) {
        binding.apply {
            record = item
            executePendingBindings()
        }
    }

}