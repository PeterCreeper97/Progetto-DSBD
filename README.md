# Progetto-DSBDProgetto DISTRIBUTED SYSTEM AND BIG DATA

Pierluigi Gaglio: 1000025301
Luca Occhipinti:  1000026003

-----------------------------------------------------

******PER DEPLOYMENT CON DOCKER*******

DOCKER-COMPOSE UP ALL'INTERNO DELLA CARTELLA DI PROGETTO

*******PER DEPLOYMENT CON KUBERNETES********

MINIKUBE START

PER LA PRIMA CONFIGURAZIONE E' NECESSARIO ABILITARE L'INGRESS:

    minikube addons enable ingress
    echo "$(minikube ip) clustera.dsbd2021.it" | sudo tee -a /etc/hosts
    kubectl create clusterrolebinding cluster-system-anonymous --clusterrole=cluster-admin --user=system:anonymous

E SUCCESSIVAMENTE:

KUBECTL APPLY -F k8s
