package com.mlcandidate.davidguedez.common.domain.model

import android.content.res.Resources
import com.mlcandidate.davidguedez.R
import java.io.IOException



class NetworkUnavailableException(message: String = Resources.getSystem().getString(R.string.no_network_available)) : IOException(message)

class NetworkException(message: String): Exception(message)