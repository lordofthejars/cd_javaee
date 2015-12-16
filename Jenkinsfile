stage 'compileAndUnit'

node {
    git branch: 'master', url: 'https://github.com/lordofthejars/cd_javaee.git'

    try {
        gradle('clean test')
    } finally {
        stash excludes: 'build/', includes: '**', name: 'source'
        stash includes: 'build/jacoco/*.exec', name: 'unitCodeCoverage'
        step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/*.xml'])
    }
}

stage 'integrationTests'

node {
    unstash 'source'
    try {
        gradle('integrationTests')
    } finally {
        stash includes: 'build/jacoco/*.exec', name: 'integrationCodeCoverage'
        step([$class: 'JUnitResultArchiver', testResults: '**/build/integrationTests-results/*.xml'])
    }
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
