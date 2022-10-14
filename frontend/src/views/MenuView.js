import Profile from "../components/Profile";
import {Box, Button, Modal, Typography} from "@mui/material";
import EmailIcon from '@mui/icons-material/Email';
import GitHubIcon from '@mui/icons-material/GitHub';
import PrivacyTipIcon from '@mui/icons-material/PrivacyTip';
import RssFeedIcon from '@mui/icons-material/RssFeed';
import {useState} from "react";

export default function MenuView(props) {
    const [aboutOpen, setAboutOpen] = useState(false);
    const [privacyOpen, setPrivacyOpen] = useState(false);
    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        border: '2px solid #000',
        boxShadow: 24,
        p: 4,
    };

    return (
        <>
            <div>
                <Modal
                    open={aboutOpen}
                    onClose={() => setAboutOpen(false)}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box sx={style}>
                        <Typography id="modal-modal-title" variant="h6" component="h2">
                            About
                        </Typography>
                        <Typography id="modal-modal-description" sx={{mt: 2}}>
                            MyRSS 는 RSS 통합 서비스입니다.
                            관심있는 블로그의 새 글들을 한 곳에서 모아보세요!
                            다시 보고 싶은 글은 북마크해보세요!
                        </Typography>
                    </Box>
                </Modal>
            </div>

            <div>
                <Modal
                    open={privacyOpen}
                    onClose={() => setPrivacyOpen(false)}
                    aria-labelledby="modal-modal-title"
                    aria-describedby="modal-modal-description"
                >
                    <Box sx={style}>
                        <Typography id="modal-modal-title" variant="h6" component="h2">
                            Privacy
                        </Typography>
                        <Typography id="modal-modal-description" sx={{mt: 2}}>
                            MyRSS는 Github 로그인만 제공하고 있습니다.
                            Github OAuth를 통해 얻은 정보 중,
                            Provider Id, Username, ImageUrl 세 가지 정보를 보관하고 있습니다.
                            회원 탈퇴 희망 시, 아래의 INQUIRY 버튼을 통해 문의주시면 감사하겠습니다.
                            (빠른 시일 내 사이트 내 탈퇴 기능을 추가하겠습니다)
                            탈퇴 문의 시 깃헙 계정에 등록된 이메일로 문의주셔야 본인 확인 후 탈퇴 처리가 가능합니다.
                            탈퇴 처리는 즉시 관련 모든 데이터를 데이터베이스에서 제거함을 의미합니다.
                            탈퇴 시 구독 정보, 북마크 정보는 복원될 수 없습니다.
                        </Typography>
                    </Box>
                </Modal>
            </div>

            <Profile userInfo={props.userInfo} loginStatus={props.loginStatus}></Profile>
            <div style={{marginTop: 50}}>
                <a href={'/manage-rss'}>
                    <Button variant="outlined" color="success"><RssFeedIcon/> &emsp;Manage RSS</Button>
                </a>

                <br/><br/><br/>

                <Button variant="outlined" color="warning" onClick={() => setAboutOpen(true)}>
                    <EmailIcon/> &emsp;About
                </Button>

                <Button variant="outlined" color="warning" onClick={() => setPrivacyOpen(true)}>
                    <PrivacyTipIcon/> &emsp;Privacy
                </Button>

                <br/>
                <a href={'https://github.com/HJ-Rich/2022-MyRSS'} target='_blank'>
                    <Button variant="outlined" color="warning"><GitHubIcon/> &emsp;Github</Button>
                </a>
                <a href={'mailTo:ztzy1907@gmail.com'} target='_blank'>
                    <Button variant="outlined" color="warning"><EmailIcon/> &emsp;Inquiry</Button>
                </a>
            </div>
        </>
    )
}
