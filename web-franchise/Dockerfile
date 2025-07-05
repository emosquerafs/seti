FROM node:22.0.0 as deps
WORKDIR /app

COPY package.json /app/
COPY angular.json /app/
COPY package-lock.json /app/
RUN npm ci
COPY ./ /app/
ENV ENVIRONMENT=dev
RUN npm run build:dev

# Include Nginx
FROM nginx:latest
COPY --from=deps /app/dist/poseidon-web/browser /usr/share/nginx/html

COPY /nginx/start_front.sh /
RUN chmod +x /start_front.sh

WORKDIR /etc/nginx/conf.d
COPY /nginx/default.conf default.conf
ENV TZ=America/Bogota
EXPOSE 80:80
ENV NODE_ENV={ENVIRONMENT}
ENTRYPOINT [ "/start_front.sh" ]