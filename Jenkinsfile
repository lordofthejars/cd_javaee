stage 'compileAndUnit'

node {
        git branch: 'master', url: 'https://github.com/lordofthejars/cd_javaee.git'

        gradle 'clean test'
        stash excludes: 'build/', includes: '**', name: 'source'
        stash includes: 'build/jacoco/*.exec', name: 'unitCodeCoverage'
        step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/*.xml'])
}

stage 'integrationTests'

node {
    unstash 'source'

    gradle 'integrationTests'
    stash includes: 'build/jacoco/*.exec', name: 'integrationCodeCoverage'
    step([$class: 'JUnitResultArchiver', testResults: '**/build/integrationTests-results/*.xml'])
}

stage 'code-analysis'

node {
    unstash 'source'
    gradle 'pmdMain'
    step([$class: 'PmdPublisher', pattern: 'build/reports/pmd/*.xml'])

    unstash 'unitCodeCoverage'
    unstash 'integrationCodeCoverage'
    gradle 'jacocoRootTestReport'
    publishHTML(target: [reportDir:'build/reports/jacoco/jacocoRootTestReport/html', reportFiles: 'index.html', reportName: 'Code Coverage'])
}

stage 'assemble-binaries'

node {
    unstash 'source'
    gradle 'assemble'
}


stage 'publish-binaries'
    unstash 'source'
    gradle 'publish'

void gradle(String tasks, String switches = null) {
    String gradleCommand = "";
    gradleCommand += './gradlew '
    gradleCommand += tasks

    if(switches) {
        gradleCommand += ' '
        gradleCommand += switches
    }

    sh gradleCommand.toString()
}
