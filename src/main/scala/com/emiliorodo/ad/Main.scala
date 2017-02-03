package com.emiliorodo.ad

import com.emiliorodo.ad.configuration.ApplicationConfigurationModule
import com.emiliorodo.ad.server._
import com.emiliorodo.ad.service.DummyServiceModule

/**
  * @author edafinov
  */
trait ConnectorRootApplicationContext
  extends ApplicationConfigurationModule
  with AkkaDependenciesModule
  with DummyServiceModule
  with HealthRoutes
  with RoutesModule

object Main extends App {

  val connector = new AppdirectConnectorBuilder()
    .build()
    
  connector.start()

}
