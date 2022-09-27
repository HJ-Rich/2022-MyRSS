import ShareIcon from '@mui/icons-material/Share';
import BookmarkAddIcon from '@mui/icons-material/BookmarkAdd';
import BookmarkAddedIcon from '@mui/icons-material/BookmarkAdded';

import {Card, CardActions, CardContent, CardHeader, IconButton, Link, Typography} from "@mui/material";

function Feed(feed) {
    const date = new Date(feed.updateDate);
    const formatDate = new Intl.DateTimeFormat('kr', {dateStyle: 'medium', timeStyle: 'short'}).format(date);

    return (
        <Card sx={{maxWidth: 500, marginTop: 5, marginBottom: 5}}>
            <Link href={feed.link} target='_blank' color={'inherit'} underline={'none'}>
                <CardHeader
                    title={feed.title}
                    subheader={formatDate}
                />
                <CardContent>
                    <Typography variant="body2" color="text.secondary">
                        {feed.description}
                    </Typography>
                </CardContent>
            </Link>
            <CardActions disableSpacing sx={{display: 'flex', justifyContent: 'flex-end'}}>
                <img src={feed.iconUrl} width={25}/>
                <div style={{fontSize: '1.1rem', padding: 4, marginLeft: 5}}>{feed.rssTitle}</div>
                <div style={{marginLeft: 10}}></div>
                <IconButton
                    aria-label="Like"
                    onClick={() => alert('subscribed!!')}
                >{
                    (feed.subscribed === true) ? <BookmarkAddedIcon/> : <BookmarkAddIcon/>
                }
                </IconButton>

                <IconButton
                    aria-label="share"
                    onClick={() => {
                        if (navigator.share) {
                            navigator.share({
                                title: feed.title,
                                url: feed.link,
                                text: feed.description
                            })
                        } else {
                            navigator.clipboard.writeText(feed.link).then(result => alert('클립보드에 복사했어요~!'));
                        }
                    }}>
                    <ShareIcon/>
                </IconButton>
            </CardActions>
        </Card>
    );
}

export default Feed;
