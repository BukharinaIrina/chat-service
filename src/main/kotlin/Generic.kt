package ru.netology

fun <K, V> displayMap(m: Map<K, V>) {
    for (key in m.keys) {
        println("$key : ${m[key]}")
    }
}

fun <L> displayList(l: List<L>) {
    for (i in l.indices) {
        println(l[i])
    }
}