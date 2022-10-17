import BottomNavBar from "../components/BottomNavBar";

export default function HowTo(props) {
    return (
        <div className="App">
            <header className="App-header">
                <div>RSS 등록 안내 💁‍️</div>

                <br/>
                <div style={{fontSize: '0.85rem', marginBottom: 20}}>
                    MyRSS는 티스토리, velog, Brunch, medium을 지원하고 있어요 <br/>
                    관심있는 블로그를 발견했을 때, 편하게 주소를 붙여넣어주세요. <br/>
                    RSS 주소는 MyRSS가 인식해서 처리해드릴게요 🙌
                </div>
                <img id="example" src="/rss-example.gif" style={{width: '80%', maxWidth: 400, marginBottom: 50}}/>

                <br/>
                <img id="rules" src="/rss-howto2.webp"
                     style={{top: '40vh', width: '100%', maxWidth: 800, marginTop: 50, marginBottom: 150}}/>
            </header>
            <footer className="App-footer">
                <BottomNavBar
                    loginStatus={props.loginStatus}
                    userInfo={props.userInfo}
                    navIndex={props.navIndex}/>
            </footer>
        </div>
    );
}
