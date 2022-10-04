import {useEffect, useState} from "react";
import axios from "axios";
import NewFeed from "../components/Feed";
import LoadingSpinner from "./LoadingSpinner";

export default function BookmarkFeeds(props) {
    const [bookmarks, setBookmarks] = useState([]);
    let pageNumber = 0;
    let loading = false;
    let hasNext = true;
    const [init, setInit] = useState(true);

    const loadMoreFeeds = (() => {
        axios.get(`${process.env.REACT_APP_API_HOST}/api/bookmarks?page=${pageNumber}${props.fetchOption}`,
            {withCredentials: true})
            .then(({data}) => {
                const newFeeds = [];
                console.log(data)
                debugger;
                data.bookmarks.forEach((feed) => newFeeds.push(feed));
                setBookmarks(presentFeeds => [...presentFeeds, ...newFeeds]);
                pageNumber = data.nextPageable.pageNumber;
                hasNext = data.hasNext;
                loading = false;
                setInit(false);
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
                    : bookmarks.map(bookmark =>
                        <NewFeed
                            key={bookmark.id}
                            id={bookmark.id}
                            title={bookmark.feed.title}
                            link={bookmark.feed.link}
                            description={bookmark.feed.description}
                            subscribed={bookmark.feed.subscribed}
                            updateDate={bookmark.feed.updateDate}
                            rssTitle={bookmark.feed.rss.title}
                            iconUrl={bookmark.feed.rss.iconUrl}
                            bookmarked={true}
                        ></NewFeed>
                    )
            }

        </>
    );
}
