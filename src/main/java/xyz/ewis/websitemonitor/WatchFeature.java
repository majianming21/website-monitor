package xyz.ewis.websitemonitor;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.ewis.websitemonitor.dto.WebsiteJobDTO;

/**
 * Feature
 *
 * @author MAJANNING
 * @date 2020/3/9
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(value = AccessLevel.PRIVATE)
public class WatchFeature {
    WebsiteJobDTO websiteJobDTO;
    boolean watched;
//    boolean noticeSuccess;
//    boolean notNotice;

    public static WatchFeature getInstance(WebsiteJobDTO websiteJobDTO) {
        WatchFeature watchFeature = new WatchFeature();
        watchFeature.setWebsiteJobDTO(websiteJobDTO);
        return watchFeature;

    }

    public WatchFeature notWatch() {
        watched = false;
        return this;
    }

//    public WatchFeature noticeSuccess() {
//        notWatch = false;
//        noticeSuccess = true;
//        return this;
//    }
//
//    public WatchFeature notNotice() {
//        notNotice = true;
//        return this;
//    }

    public WatchFeature watched() {
        this.watched=true;
        return this;
    }

    public WatchFeature setWatched(boolean watch) {
        this.watched = watched;
        return this;
    }




/*
    static class FeatureState {
        Feature feature;

        private FeatureState(Feature feature) {
            this.feature = feature;
        }
        public  Feature notWatch() {
            feature.setNotWatch(true);
            return feature;
        }

        public Feature noticeSuccess() {
            feature.setNoticeSuccess(true);
            return feature;
        }
    }
    public static FeatureState getInstance(WebsiteJobDTO websiteJobDTO) {
        return new FeatureState(new Feature());

    }*/
}
