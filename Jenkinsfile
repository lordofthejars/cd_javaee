stage 'compileAndUnit'

node {
    git branch: 'master', url: 'https://github.com/lordofthejars/cd_javaee.git'

    try {
        gradle('clean test')
    } finally {
        stash excludes: 'build/', includes: '**', name: 'source'
        stash includes: 'build/jacoco/*.exec', name: 'codeCoverage'
        step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/*.xml'])
    }
}

stage 'integrationTests'

node {
    echo 'Integration Tests'
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
