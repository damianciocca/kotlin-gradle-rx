package example7FilesAnManyObservables

import java.io.File


fun main(args: Array<String>) {

    val fileInfo = fileToFileInfoOld(File("/Users/damian/Documents/etermax-codestyle"))
    println("fileInfo = ${fileInfo}")

    val fileInfo2 = fileToFileInfoOld(File("/Users/damian/Documents/books"))
    println("fileInfo2 = ${fileInfo2}")
}

/**
 * De la manera clasica
 */

fun fileToFileInfoOld(file: File): FileInfo {

    if (file.isFile)
        return FileInfo(file, file.length())
    else if (file.isDirectory) {
        val fileInfo = FileInfo(file)
        file.listFiles().forEach { subFile ->
            val subFileInfo = fileToFileInfoOld(subFile)
            fileInfo.filesCount += subFileInfo.filesCount
            fileInfo.bytes += subFileInfo.bytes
        }
        return fileInfo
    }

    throw IllegalArgumentException("Neither file or directory: $file")

}

/**
 * De manera reactiva
 */

//fun fileToFileInfo(file: File): Observable<FileInfo> {
//    if (file.isFile()) {
//        return Observable.just(FileInfo(file, file.length()))
//    } else if (file.isDirectory) {
//
//        return Observable.fromArray(file.listFiles())
//                .flatMap({ fileToFileInfo(it) })
//                .reduce(FileInfo(file), { accumulator, subFileInfo ->
//                    accumulator.bytes += subFileInfo.bytes
//                    accumulator.filesCount += subFileInfo.filesCount
//                    accumulator
//                })
//
//    }
//    return Observable.error(IllegalArgumentException("$file is neither a file or a directory"))
//}
