pipeline{
    agent any

    stages {
        stage('pull') {
            steps {
                git branch: 'main', changelog: false, credentialsId: 'ssh-key', poll:false, url: 'https://github.com/chetansomkuwar254/jenkins-b1.git'
                echo 'data pulling is done'
            }
        }
        stage('build') {
            steps {
                sh '/opt/apache-maven-3.9.6/bin/mvn clean package'
                echo 'buildup is ready'
            }
        }
        stage('test') {
            steps {
               sh ''' /opt/apache-maven-3.9.6/bin/mvn sonar:sonar \
                     -Dsonar.projectKey=student-app \
                     -Dsonar.host.url=http://3.95.19.120:9000 \
                     -Dsonar.login=6a120818611d31dc2054bc3c4b904c226fdceb48 '''

            }
        }
        stage('quality test') {
            steps {
                echo 'quality check is done'
            }
        }
        stage('deploy') {
            steps {
                sh " sudo cp /var/lib/jenkins/workspace/student-app/target/studentapp-ui-app-1.0-SNAPSHOT.jar   /root/tomcat/webapps/ "
                echo 'deployment is done'
            }
        }
    }
}
