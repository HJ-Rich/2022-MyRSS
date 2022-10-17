import {useEffect, useState} from "react";
import axios from "axios";
import LoadingSpinner from "./LoadingSpinner";
import Feed from "../components/Feed";
import {NavLink} from "react-router-dom";

export default function SubscribedFeeds() {
    const [feeds, setFeeds] = useState(new Set());
    let pageNumber = 0;
    let loading = false;
    let hasNext = true;
    let isEmpty = false;
    const [init, setInit] = useState(true);

    const loadMoreFeeds = (() => {
        axios.get(`${process.env.REACT_APP_API_HOST}/api/subscribes?page=${pageNumber}`,
            {withCredentials: true})
            .then(({data}) => {
                setFeeds(presentFeeds => {
                    if (pageNumber === 0 && data.feedResponses.length === 0) {
                        isEmpty = true;
                    }

                    const present = JSON.stringify(presentFeeds);

                    const feedsToPush = []
                    data.feedResponses.forEach(feed => {
                            if (!present.includes(JSON.stringify(feed))) {
                                feedsToPush.push(feed)
                            }
                        }
                    );
                    return [...presentFeeds, ...feedsToPush];
                });

                pageNumber = data.nextPageable.pageNumber;
                hasNext = data.hasNext;
                loading = false;
                setInit(false);

                if (!hasNext && !isEmpty) {
                    document.getElementById('bottomNotifier').style.display = 'inherit';
                }
            })
            .catch(error => {
                console.log(error);
                loading = false;
            })
    });

    const handleScroll = (event) => {
        if (loading || !hasNext) {
            return;
        }

        if (window.innerHeight + event.target.documentElement.scrollTop + 1 >=
            event.target.documentElement.scrollHeight
        ) {
            loading = true;
            loadMoreFeeds();
        }
    }

    useEffect(() => {
        loadMoreFeeds();
        window.addEventListener('scroll', (event) => handleScroll(event))
    }, []);

    return (
        <>
            {
                init ? <LoadingSpinner/>
                    :
                    feeds.length === 0 ?
                        <div>
                            <div>아직 구독하는 RSS가 없어요 😃</div>
                            <div>&emsp;</div>
                            <div><NavLink to={'/rss'} style={{color: 'inherit', fontWeight: 500}}>RSS 추가하러 가기</NavLink>
                            </div>
                        </div>
                        :
                        feeds.map(feed =>
                            <Feed
                                key={feed.id}
                                id={feed.id}
                                title={feed.title}
                                link={feed.link}
                                description={feed.description}
                                subscribed={feed.subscribed}
                                updateDate={feed.updateDate}
                                rssTitle={feed.rss.title}
                                iconUrl={feed.rss.iconUrl}
                                bookmarked={feed.bookmarked}
                            ></Feed>
                        )
            }
            <div id="bottomNotifier"
                 style={{display: 'none', marginTop: 100, marginBottom: 200}}>
                더 이상 불러올 피드가 없습니다 🙌
            </div>
        </>
    );
}
