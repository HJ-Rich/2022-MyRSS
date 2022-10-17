import {useEffect, useState} from "react";
import axios from "axios";
import {NavLink} from "react-router-dom";
import LoadingSpinner from "./LoadingSpinner";
import Feed from "../components/Feed";

export default function BookmarkFeeds() {
    const [bookmarks, setBookmarks] = useState(new Set());
    let pageNumber = 0;
    let loading = false;
    let hasNext = true;
    let isEmpty = false;
    const [init, setInit] = useState(true);

    const loadMoreFeeds = (() => {
        axios.get(`${process.env.REACT_APP_API_HOST}/api/bookmarks?page=${pageNumber}`,
            {withCredentials: true})
            .then(({data}) => {
                if (pageNumber === 0 && data.bookmarks.length === 0) {
                    isEmpty = true;
                }

                setBookmarks(presentFeeds => {
                    const present = JSON.stringify(presentFeeds);
                    const feedsToPush = []

                    data.bookmarks.forEach(feed => {
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
                    :
                    bookmarks.length === 0 ?
                        <div>
                            <div>아직 북마크한 피드가 없어요 😃</div>
                            <div>&emsp;</div>
                            <div><NavLink to={'/'} style={{color: 'inherit', fontWeight: 500}}>북마크 추가하러 가기</NavLink>
                            </div>
                        </div>
                        :
                        bookmarks.map(bookmark =>
                            <Feed
                                key={bookmark.id}
                                id={bookmark.feed.id}
                                title={bookmark.feed.title}
                                link={bookmark.feed.link}
                                description={bookmark.feed.description}
                                subscribed={bookmark.feed.subscribed}
                                updateDate={bookmark.feed.updateDate}
                                rssTitle={bookmark.feed.rss.title}
                                iconUrl={bookmark.feed.rss.iconUrl}
                                bookmarked={true}
                            ></Feed>
                        )
            }
            <div id="bottomNotifier" style={{display: 'none', marginTop: 100, marginBottom: 200}}>모두 불러왔어요 🙌
            </div>
        </>
    );
}
