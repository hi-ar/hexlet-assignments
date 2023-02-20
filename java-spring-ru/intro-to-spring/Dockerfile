FROM gradle:7.4.0-jdk17

WORKDIR /java-spring-ru/intro-to-spring/

COPY / .

RUN gradle installDist

CMD ./build/install/intro-to-spring/bin/intro-to-spring