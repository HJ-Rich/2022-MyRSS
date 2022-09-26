import {useEffect, useState} from "react";
import axios from "axios";
import NewFeed from "../components/Feed";

export default function DefaultFeeds() {
    const [feeds, setFeeds] = useState([]);
    let pageNumber = 0;
    let loading = false;
    let hasNext = true;

    const loadMoreFeeds = (() => {
        axios.get(`${process.env.REACT_APP_API_HOST}/api/feeds?page=${pageNumber}`)
            .then(({data}) => {
                const newFeeds = [];
                data.feedResponses.forEach((feed) => newFeeds.push(feed));
                setFeeds(presentFeeds => [...presentFeeds, ...newFeeds]);
                pageNumber = data.nextPageable.pageNumber;
                hasNext = data.hasNext;
                loading = false;
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
                feeds.map(feed =>
                    <NewFeed
                        key={feed.id}
                        id={feed.id}
                        title={feed.title}
                        link={feed.link}
                        description={feed.description}
                        updateDate={feed.updateDate}
                        rssTitle={feed.rss.title}
                        iconUrl={feed.rss.iconUrl}
                    ></NewFeed>
                )
            }

        </>
    );
}
