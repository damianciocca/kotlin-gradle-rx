package vertx.clientservertest

import io.reactivex.Completable
import io.vertx.core.Future
import io.vertx.reactivex.core.AbstractVerticle
import io.vertx.reactivex.core.buffer.Buffer
import io.vertx.reactivex.core.file.FileSystem
import org.yaml.snakeyaml.Yaml

/**
 * Punto de partida de la APP
 * 1. carga configuracion
 * 2. y cuando termina deploya el HHTP server
 */
class MainVerticle : AbstractVerticle() { // IMPORTANTE extender de io.vertx.reactivex.core.AbstractVerticle

    override fun start(startFuture: Future<Void>) {

        loadEnvironmentConfig(vertx.fileSystem())
                .concatWith(deployHttpServerVerticle())
                .subscribe(
                        { startFuture.complete() },
                        { startFuture.fail(it) })
    }

    private fun deployHttpServerVerticle(): Completable =
            vertx.rxDeployVerticle(MyFirstVertxHttpServer::class.java.name).toCompletable()


    private fun loadEnvironmentConfig(fileSystem: FileSystem): Completable =
            fileSystem.rxReadFile("secrets.yml")
                    .map { loadYaml(it) }
                    .doOnSuccess { EnvironmentConfig.set(it) }
                    .toCompletable()

    private fun loadYaml(it: Buffer) = Yaml().load<Map<String, Map<*, *>>>(it.toString())
}