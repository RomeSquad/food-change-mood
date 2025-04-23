package domain

import java.lang.Exception

class InvalidFileExtensionException(message:String): Exception(message)

class NoMealsFoundException(message:String): Exception(message)