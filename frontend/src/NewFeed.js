import ShareIcon from '@mui/icons-material/Share';
import FavoriteIcon from '@mui/icons-material/Favorite';

import {Card, CardActions, CardContent, CardHeader, IconButton, Link, Typography} from "@mui/material";

function NewFeed(feed) {
    const date = new Date(feed.updateDate);
    const formatDate = new Intl.DateTimeFormat('kr', {dateStyle: 'medium', timeStyle: 'short'}).format(date);

    return (
        <Card sx={{maxWidth: 500, marginTop: 5}}>
            <Link href={feed.link} target='_blank' color={'inherit'}>
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
            <CardActions disableSpacing>
                <IconButton aria-label="add to favorites">
                    <FavoriteIcon/>
                </IconButton>
                <IconButton aria-label="share">
                    <ShareIcon onClick={() => window.navigator.share({
                        url: feed.url,
                        text: feed.description,
                        title: feed.title
                    })}/>
                </IconButton>
            </CardActions>
        </Card>
    );
}

export default NewFeed;
