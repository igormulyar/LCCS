package model;

/**
 * Created by TRUST on 08.11.2016.
 */
public class Court {

    // TODO: extract to separate file (in model package)
    // I thought it would be better to keep it nested (as private inner class inside HttpExtractor)
    // because it was created especially for building http requests
    // and it shouldn't be used somewhere else - only in HttpExtractor
    // In my opinion those made encapsulation level stronger

        private String name;
        private String idInNumber;
        private String courtId;
        private String url;
        private String host;
        private String referer;

        public String getName() {
            return name;
        }

        public String getIdInNumber() {
            return idInNumber;
        }

        public String getCourtId() {
            return courtId;
        }

        public String getUrl() {
            return url;
        }

        public String getHost() {
            return host;
        }

        public String getReferer() {
            return referer;
        }

}
