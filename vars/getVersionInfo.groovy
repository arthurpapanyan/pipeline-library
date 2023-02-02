
import groovy.transform.SourceURI
import java.nio.file.Path
import java.nio.file.Paths

class ScriptSourceUri {
    @SourceURI
    static URI uri
}

def call() {
    Path scriptLocation = Paths.get(ScriptSourceUri.uri)
    echo()
    def props = readJSON file: scriptLocation.getParent().getParent().resolve('resources/config.json').toString()
    return props
}
