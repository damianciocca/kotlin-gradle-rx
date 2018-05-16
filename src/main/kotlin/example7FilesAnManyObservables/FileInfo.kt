package example7FilesAnManyObservables

import java.io.File

data class FileInfo(val file: File,
                    var bytes: Long = 0,
                    var filesCount: Long = 1)