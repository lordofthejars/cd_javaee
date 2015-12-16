stage 'compileAndUnit'

node {
    git branch: 'cd_javaee', url: 'https://github.com/lordofthejars/cd_javaee.git'

    try {
        gradle('clean test')
    } finally {
        step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/*.xml'])
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