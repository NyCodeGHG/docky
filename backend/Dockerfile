FROM golang:alpine AS builder

WORKDIR /build
COPY . .

ARG OS
ENV GOOS=$OS
ARG ARCH
ENV GOARCH=$ARCH

RUN go build .

FROM alpine

WORKDIR /app
COPY --from=builder /build/docky /docky
EXPOSE 3000
ENTRYPOINT ["/docky"]
