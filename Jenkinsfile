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

stage 'code-quality'

node {
    unstash 'source'
    gradle 'pmdMain'

    unstash 'unitCodeCoverage'
    unstash 'integrationCodeCoverage'
    gradle 'jacocoRootTestReport'
}

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
