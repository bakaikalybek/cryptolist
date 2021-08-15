package kg.bakai.cryptolist.utils

import kg.bakai.cryptolist.R

class EmptyBodyException(errorMessage: String? = null) : RuntimeException(errorMessage),
    TranslatableException {
    override fun getDefaultResourceId(): Int = R.string.error_empty_body
}

class NetworkUnavailableException(errorMessage: String? = null) : RuntimeException(errorMessage),
    TranslatableException {
    override fun getDefaultResourceId(): Int = R.string.error_no_internet
}

class TimeOutException(errorMessage: String? = null) : RuntimeException(errorMessage),
    TranslatableException {
    override fun getDefaultResourceId(): Int = R.string.error_network_timeout
}

class ParseDataException(errorMessage: String? = null) : RuntimeException(errorMessage),
    TranslatableException {
    override fun getDefaultResourceId(): Int = R.string.error_data_parse
}

