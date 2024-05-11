package com.simplespace.android.lib.standard.serialization

import com.simplespace.android.lib.standard.iterable.list.zipMap
import com.simplespace.android.lib.standard.iterable.map.toList

class SimpleSerialization <T, V> (

    inline val map: (T) -> List<V>,
    inline val data: (List<V>) -> T
){
    companion object {

        fun <T, K, V> encodable(map: T.() -> Map<K, V>) : (List<K>) -> (T) -> List<V> =

            { keys ->
                {

                    it.map().toList(keys)
                }
            }

        fun <T, K, V> decodable( data: Map<K, V>.() -> T): (List<K>) -> (List<V>) -> T =

            { keys ->
                {

                    keys.zipMap(it).data()
                }
            }

        fun <T, K, V>getInstance(
            sortedKeys: List<K>,
            mapper: T.() -> Map<K, V>,
            deMapper: Map<K, V>.() -> T,
        ) = SimpleSerialization(
            map = encodable(mapper)(sortedKeys),
            data = decodable(deMapper)(sortedKeys)
        )
    }
}
