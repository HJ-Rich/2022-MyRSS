import {useEffect, useState} from "react";
import axios from "axios";
import LoadingSpinner from "./LoadingSpinner";
import Feed from "../components/Feed";

export default function DefaultFeeds() {
    const [feeds, setFeeds] = useState([]);
    let pageNumber = 0;
    let loading = false;
    let hasNext = true;
    const [init, setInit] = useState(true);

    const loadMoreFeeds = (() => {
        axios.get(`${process.env.REACT_APP_API_HOST}/api/feeds?page=${pageNumber}`,
            {withCredentials: true})
            .then(({data}) => {
                setFeeds(presentFeeds => {
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

                if (!hasNext) {
                    setTimeout(() => document.getElementById('bottomNotifier').style.display = 'inherit', 200)
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
                    : feeds.map(feed =>
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
            <div id="bottomNotifier" style={{display: 'none', marginTop: 100, marginBottom: 200}}>모두 불러왔어요 🙌
            </div>
        </>
    );
}
