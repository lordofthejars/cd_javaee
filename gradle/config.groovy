binaryRepository {
    url = 'file:/${project.projectDir}/artifacts'
}
environments {
    test {
        server {
            hostname = 'localhost'
            port = 8080
        }
    }
}