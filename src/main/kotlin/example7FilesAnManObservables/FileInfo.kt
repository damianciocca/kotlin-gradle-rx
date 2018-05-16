package example7FilesAnManObservables

import java.io.File

data class FileInfo(val file: File,
                    var bytes: Long = 0,
                    var filesCount: Long = 1)