package com.mlcandidate.davidguedez.searchproduct.presentation.model

import android.content.res.Resources
import com.mlcandidate.davidguedez.R

class EmptyQueryException(message: String = Resources.getSystem().getString(R.string.no_query_search_info)): Exception(message)