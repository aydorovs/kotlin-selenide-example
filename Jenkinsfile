// Powered by Infostretch 

timestamps {

node () {

	stage ('KOTLIN_ALLURE_API - Checkout') {
 	 checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '5d8135b4-04ae-417a-92ef-bae4ece445f2', url: 'https://github.com/aydorovs/kotlin-selenide-example']]]) 
	}
	stage ('KOTLIN_ALLURE_API - Build') {
 	
withEnv(["JAVA_HOME=${ tool '"+JDK+"' }", "PATH=${env.JAVA_HOME}/bin"]) { 

// Unable to convert a build step referring to "hudson.plugins.ws__cleanup.PreBuildCleanup". Please verify and convert manually if required.		// Maven build step
	withMaven(jdk: '', maven: 'mvn') { 
 			if(isUnix()) {
 				sh "mvn clean test -D tests=test_api -Dhost=aydorovs.ddns.net " 
			} else { 
 				bat "mvn clean test -D tests=test_api -Dhost=aydorovs.ddns.net " 
			} 
 		} 
	}
}
}
}