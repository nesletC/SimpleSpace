package com.simplespace.android.lib.standard.array


inline fun <T>ByteArray.setAll(block: ((Byte) -> Unit) -> T) : T {

    var index = 0

    return block {
        set(index, it)
        index++
    }
}


inline fun ByteArray.init(block: ((Byte) -> Unit) -> Unit) : ByteArray = apply{

    setAll(block)
}


fun ByteArray.copyOfLength(fromIndex: Int, length: Int) : ByteArray =
    copyOfRange(fromIndex = fromIndex, toIndex = fromIndex + length)


inline fun ByteArray.transform(block: (Byte) -> Byte) {
    for (i in indices) {
        set(i, block(get(i)))
    }
}


inline fun ByteArray.forEachIndex(block: (Int) -> Byte) {
    for (i in indices){
        set(i, block(i))
    }
}


fun ByteArray.setAll(byteArray: ByteArray, position: Int = 0) : Int {

    val bufferSpace = size - position
    val length =
        if (bufferSpace > byteArray.size)
            bufferSpace
        else
            byteArray.size

    for (index in 0..<length) {
        set(index + position, byteArray[index])
    }

    return length
}


//__________________________________________________________________________________________________


inline fun <T>IntArray.setAll(block: ((Int) -> Unit) -> T) : T {

    var index = 0

    return block {
        set(index, it)
        index++
    }
}


inline fun IntArray.init(block: ((Int) -> Unit) -> Unit) : IntArray = apply{

    setAll(block)
}


fun IntArray.copyOfLength(fromIndex: Int, length: Int) : IntArray =
    copyOfRange(fromIndex, fromIndex + length)


inline fun IntArray.transform(block: (Int) -> Int) {
    for (i in indices) {
        set(i, block(get(i)))
    }
}


inline fun IntArray.forEachIndex(block: (Int) -> Int) {
    for (i in indices){
        set(i, block(i))
    }
}


fun IntArray.setAll(intArray: IntArray, position: Int = 0) : Int {

    val bufferSpace = size - position
    val length =
        if (bufferSpace > intArray.size)
            bufferSpace
        else
            intArray.size

    for (index in 0..<length) {
        set(index + position, intArray[index])
    }

    return length
}


//__________________________________________________________________________________________________


inline fun <T>CharArray.setAll(block: ((Char) -> Unit) -> T) : T {

    var index = 0

    return block {
        set(index, it)
        index++
    }
}


inline fun CharArray.init(block: ((Char) -> Unit) -> Unit) : CharArray = apply{

    setAll(block)
}


inline fun charArrayOf(size: Int, block: ((Char) -> Unit) -> Unit) : CharArray =
    CharArray(size).apply{
        setAll(block)
    }


fun CharArray.copyOfLength(fromIndex: Int, length: Int) : CharArray =
    copyOfRange(fromIndex, fromIndex + length)


inline fun CharArray.transform(block: (Char) -> Char) {
    for (i in indices) {
        set(i, block(get(i)))
    }
}


inline fun CharArray.forEachIndex(block: (Int) -> Char) {
    for (i in indices){
        set(i, block(i))
    }
}


fun CharArray.setAll(charArray: CharArray, position: Int = 0) : Int {

    val bufferSpace = size - position
    val length =
        if (bufferSpace > charArray.size)
            bufferSpace
        else
            charArray.size

    for (index in 0..<length) {
        set(index + position, charArray[index])
    }

    return length
}