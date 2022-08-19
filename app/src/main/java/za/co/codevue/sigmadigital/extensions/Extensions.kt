package za.co.codevue.sigmadigital.extensions

import androidx.paging.PagingData
import androidx.paging.insertSeparators
import za.co.codevue.sigmadigital.ui.common.PagingListModel

fun PagingData<PagingListModel.Data>.addSeparators(): PagingData<PagingListModel> =
    this.insertSeparators { before, after ->
        when {
            before == null -> null
            after == null -> null
            else -> PagingListModel.Separator("Separator: $before - $after")
        }
    }