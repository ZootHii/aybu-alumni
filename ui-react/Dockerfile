FROM node:16.14.2

WORKDIR /ui-react

ENV PATH /ui-react/node_modules/.bin:$PATH

COPY package.json ./
COPY package-lock.json ./
RUN npm install

COPY . ./

CMD ["npm", "start"]