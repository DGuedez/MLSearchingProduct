package com.mlcandidate.davidguedez.common.domain.model

import java.io.IOException



class NetworkUnavailableException(message: String = "No hay servicio de red disponible.") : IOException(message)

class NetworkException(message: String): Exception(message)