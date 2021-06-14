package com.example.mycalendar.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MaximDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MAXIM_TABLE";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "MAXIM_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_MAXIM = "MAXIM";

    public MaximDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MAXIM + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void insertMaxim(String maxim) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_MAXIM, maxim);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void insertListMaxim() {
        insertMaxim("Học... Học để là chính mình, và học để từ bỏ với vẽ thanh cao những gì không phải là mình. - H.F.Amiel");
        insertMaxim("Tuần lễ của người chăm chỉ có bảy ngày, còn tuần lễ của kẻ lười biến có bảy ngày mai. - Ngạn ngữ Đức");
        insertMaxim("Về quê gặp bến sông quê, thời gian đã vắt qua đê lối mòn, dẫu cho biết mẹ không còn, bến xưa nhòe lệ mắt con vẫn tìm. - Xuân Hoài");
        insertMaxim("Ngọc không giũa không thành đồ đẹp, người không học không biết lẽ phải. - Lễ Ký");
        insertMaxim("Rễ của sự học tập thì đắng, quả của sự học tập thì ngọt. - Ngạn ngữ Nga");
        insertMaxim("Người sáng suốt mà chăm học thì không lấy việc hỏi kẻ kém hơn mình là xấu hổ. - Khổng Tử");
        insertMaxim("Người học cao hiểu rộng mà không mang tri thức giúp ích cho đời thì cũng giống như người nông dân, đã cày xới mà không gieo hạt. - Khổng Tử");
        insertMaxim("Người có học không phải người biết nhiều mà là biết rõ những gì mình phải biết và hiễu rõ những gì mình đã biết. - Marius Grout");
        insertMaxim("Muốn đánh giá người trước tiên tự đánh giá mình, nói lời tổn hại người khác ngược lại tổn hại mình. Ngậm máu phun người dơ miệng mình. - Thái Công");
        insertMaxim("Không phải những người cười với ta đều là bạn ta, cũng không phải tất cả những người làm ta bực mình đều là kẻ thù của ta. - Ngoạn ngữ Mông Cổ");
        insertMaxim("Người đàn ông đích thực luôn biết cách để chuộc lỗi của mình, không đơn thuần chỉ là nói xin lỗi. - Phan Nhân");
        insertMaxim("Viên mãn nhất trong tình cảm không phải là nắm giữ được bàn tay của một người nhất nhất không rời, mà là khi trải qua bao nhiêu mất mát đổi thay, họ vẫn về để nắm lấy tay bạn. - Phan Nhân");
        insertMaxim("Bạn có thể yêu hay ghét, thích hay không còn thích nữa. Chỉ cần thành thật, bạn sẽ luôn luôn thanh thản. - Phạm Lữ Ân");
        insertMaxim("Bạn sẽ biết rằng đó là tình yêu khi tất cả những gì bạn muốn là người đó hạnh phúc cho dù niềm hạnh phúc đó không có phần của bạn. - Julia Roberts");
        insertMaxim("Để trưởng thành bạn cần phải đánh đổi bằng nhiều mối quan hệ và đánh mất rất nhiều người gọi là bạn. - Phan Nhân");
        insertMaxim("Sẽ có lúc bạn buồn mà chẳng biết tại sao. Cảm giác giống như đã đánh rơi thứ gì đó rất quan trọng nhưng chẳng thể nhớ đó là gì. - Phan Nhân");
        insertMaxim("Chúng mình thức dậy mỗi ngày đều mong thấy được mình bên người mình yêu. Thiên đường của người này, đôi khi, chỉ là ánh mắt, bờ vai, tấm lưng, đôi bàn tay của người kia. - Phan Nhân");
        insertMaxim("Bạn không thể điều khiển hướng gió, chỉ có thể điều khiển cánh buồm.Bạn không cần phải thấy hết các bậc thang mà chỉ cần đi bước đầu tiên với một niềm tin. - Martin Luther King");
        insertMaxim("Nghịch lý thường nằm ở chỗ, chúng ta cứ thường phán xét cuộc sống của người khác dựa trên quan điểm cá nhân, rồi lại loay hoay tìm cách sống sao cho hợp với đánh giá theo quan điểm của người khác. - Nguyễn Ngọc Thạch");
        insertMaxim("Có bao nhiêu người trên thế gian này, trái tim chung nhịp nhưng không thể cầm tay đi chung trên một con đường? Nhân duyên này sẽ khổ đau nhưng đó chính là thứ nhân duyên khiến bạn trở nên mạnh mẽ. - Phan Ý Yên");
        insertMaxim("Thành công không phải chìa khóa mở cửa hạnh phúc. Hạnh phúc là chìa khóa dẫn tới thành công. Nếu bạn yêu điều bạn đang làm, bạn sẽ thành công. - Phan Nhân");
        insertMaxim("Ra đi không phải bao giờ cũng đồng nghĩa với một sự từ bỏ, đó cũng có thể là một cách để giữ gìn những gì đã trải qua, nếu người ta biết ra đi trước khi quá trễ. - Marc Levy");
        insertMaxim("Thời gian làm cho người ta tha hóa bớt dần sự bao dung, độ lượng và ích kỉ cứ đầy dần trong túi áo. Kể cả tôi trong những ngày này, ngoài gia đình ra chẳng còn sợ mất điều gì cả. - Phan Nhân");
        insertMaxim("Tình yêu trong xa cách ví như ngọn lửa trong gió. Gió thổi tắt ngọn lửa nhỏ và thổi bùng ngọn lửa lớn. - Bussy Rebutin");
        insertMaxim("Sự làm việc giúp ta thoát khỏi ba cái ác là buồn chán, tật xấu và túng thiếu. - Voltaire");
        insertMaxim("Những ai không biết tự trọng thì cũng khó mà đòi hỏi người khác tôn trọng mình. - Phan Văn Nhân");
        insertMaxim("Đời sẽ dịu dàng biết mấy khi tất cả con người biết đặt mình vào vị trí người khác. - Phan Văn Nhân");
        insertMaxim("Nhân nghĩa chớ dính tiền, dính tiền nhân nghĩa tuyệt. Đường xa mới biết sức ngựa, sống lâu mới rõ lòng người. - Lời Phật");
        insertMaxim("Mặt trời nhìn đẹp nhất khi mọc ở phía đông và lặng ở phía tây, tính nết con người nhìn rõ nhất khi còn bé và giờ phút hấp hối. - Bohr");
        insertMaxim("Không phải tất cả những người cười với ta đều là bạn, cũng không phải tất cả những người làm ta bực mình đều là kẻ thù. - Ngoạn ngữ Mông Cổ");
        insertMaxim("Thiện là bị động, nó phục tùng lý trí, ác là chủ động, nó sinh ra hành động. Thiện là thiên đường, ác là địa ngục. - Block");
        insertMaxim("Thuốc hay thì đắng miện nhưng có lợi cho người bệnh, lời hay thì trái tai người nhưng có lợi cho công việc. - Tiêu Hà");
        insertMaxim("Hãy đễ cho người chết đi tìm sự bất tử trong danh vọng và những người sống đi tìm sự bất tử trong tình yêu. - Tagore");
        insertMaxim("Cuộc đời rất ngắn ngủi, và việc nó có ngọt ngào hay không là phụ thuộc vào bạn. - Sarah Louise Delany");
        insertMaxim("Thế giới là thánh đường của tôi. Hành động là lời cầu nguyện của tôi. Hành vi là tín điều của tôi. - Steve Maraboli");
        insertMaxim("Và cuộc đời bạn khi nó đang diễn ra chẳng bao giờ tạo thành bầu không khí cho tới khi nó trở thành hồi ức. - Andy Warhol");
        insertMaxim("Người nghệ sĩ là người tạo ra thứ mà người ta không cần phải có. - Andy Warhol");
        insertMaxim("Hãy mơ giấc mơ của mình; và nhận ra rằng bạn không chỉ là người mơ, bạn là điểm khởi đầu nơi giấc mơ trở thành hiện thực. - Steve Maraboli");
        insertMaxim("Tôi nghĩ luôn yêu đời chính là chìa khóa dẫn đến tuổi trẻ vĩnh hằng. - Doug Hutchison");
        insertMaxim("Mối liên hệ gắn kết gia đình chân chính của bạn không phải là huyết thống, mà là sự tôn trọng và niềm vui trong đời nhau. - Richard Bach");
        insertMaxim("Lối nghĩ coi mình là nạn nhân tạo ra ảo tưởng về lầm lỗi và oán trách, thứ che mắt khiến bạn không thấy được sự thật đơn giản về nguyên nhân và hệ quả. - Steve Maraboli");
        insertMaxim("Việc bạn đi chậm thế nào không quan trọng, miễn là bạn không ngừng bước. - Andy Warhol");
        insertMaxim("Những người thành công luôn không ngừng vận động. Họ có thể phạm sai lầm, nhưng họ không bỏ cuộc. - Conrad Hilton");
        insertMaxim("Bằng việc phản ứng lại nỗi sợ hãi thay vì đáp ứng lại tình yêu thương, bạn bơm thuốc độc trực tiếp vào mạch máu của mối quan hệ. - Steve Maraboli");
        insertMaxim("Bạn có yêu bản thân đủ nhiều để TRỞ THÀNH con người mà bạn yêu bản thân đủ nhiều để MONG MUỐN hay không? - Steve Maraboli");
        insertMaxim("Nếu bạn tụt lại phía sau, hãy chạy nhanh hơn. Đừng bao giờ bỏ cuộc, đừng bao giờ đầu hàng, và hãy vượt lên tình thế bất lợi. - Jesse Jackson");
        insertMaxim("Anh hùng không can đảm hơn người bình thường, anh ta chỉ can đảm lâu hơn năm phút. - Ralph Waldo Emerson");
        insertMaxim("Có sự nhiệt huyết rõ ràng đối với tự do, khiến bản tính con người vượt lên trên chính nó, trong những hành động can đảm và anh hùng. - Alexander Hamilton");
        insertMaxim("Một khi nhà nước hình thành, không còn có anh hùng nữa. Họ chỉ xuất hiện trong những điều kiện không có văn minh. - Hegel");
        insertMaxim("Một bậc anh hùng cứu nạn, giúp nguy, thì cốt nhất là phải lao tâm, khổ lực. - Tăng Quốc Phiên");
        insertMaxim("Thật khốn khổ cho một đất nước không có anh hùng, nhưng còn khốn khổ hơn cho một đất nước có anh hùng, nhưng không ghi nhớ và vinh danh họ. - Marcus Tullius Cicero");
        insertMaxim("Nói ngắn gọn, sự anh hùng là làm điều đúng bất chấp hậu quả. - Brandon Mull");
        insertMaxim("Nhưng anh biết không, tôi thấy thân thiết với những kẻ bại trận hơn với những vị thánh. Tính anh hùng và sự thiêng liêng không hấp dẫn tôi. Điều làm tôi thích thú là được làm con người. - Albert Camus");
        insertMaxim("Anh hùng là những người bình thường biến mình trở thành phi thường. - Gerard Way");
        insertMaxim("Tôi có nơi ẩn náu tuyệt vời, đó là gia đình. Tôi có mối quan hệ tuyệt vời với anh chị em của tôi; điều này khiến tôi cảm thấy mình luôn biết mình thuộc về nơi đâu. - Jose Carreras");
        insertMaxim("Đường không qua lại cỏ gianh mọc.Anh em không đi về thành người dưng. - Tục ngữ Tày-Nùng");
        insertMaxim("Chúng ta đã bay trông không trung như chim và bơi dưới đại dương như cá, nhưng vẫn chưa học được hành động đơn giản là đi trên mặt đất như anh em. - Martin Luther King Jr.");
        insertMaxim("Chúng ta đều đi những con đường khác nhau trong đời, nhưng dù chúng ta đi tới đâu, chúng ta cũng mang theo mình một phần của nhau. - Khuyết danh");
        insertMaxim("Bạn xấu cũng có tính truyền thụ như sự phóng túng. Con người đem sự xóa bỏ thành kiến ra bù cho sự trong trắng đánh mất. - Denis Diderot");
        insertMaxim("Người ảnh hưởng tới bạn là người tin vào bạn. - Henry Drummond");
        insertMaxim("Quần áo làm nên con người. Những kẻ trần truồng không có hay có rất ít ảnh hưởng lên xã hội. - Mark Twain");
        insertMaxim("Đừng để quan điểm của người khác về bạn trở thành hiện thực của bạn. - Les Brown");
        insertMaxim("Ảnh hưởng của người thầy là vĩnh cửu. Ta không thể biết được khi nào thì ảnh hưởng ấy dừng. - Henry Brooks Adams");
        insertMaxim("Một mình tôi không thể thay đổi thế giới, nhưng tôi có thể ném hòn đá lên mặt nước để tạo nhiều con sóng. - Mẹ Teresa");
        insertMaxim("Một chiếc đinh gỉ đặt cạnh cái la bàn trung thực sẽ lôi kéo nó rời khỏi sự thật, và làm đắm con tàu. - Walter Scott");
        insertMaxim("Những tư tưởng tốt nhất của chúng ta đến từ người khác. - Ralph Waldo Emerson");
        insertMaxim("Bạn đồng hành xấu làm tha hóa tính cách tốt. - Menander");
        insertMaxim("Đừng phấn đấu để thành công mà hãy phấn đấu để mình có ích. - Albert Einstein");
        insertMaxim("Tôi chưa thất bại. Tôi chỉ là đã tìm ra 10,000 cách không hoạt động. - Thomas Edison");
        insertMaxim("Để thành công trong cuộc sống, bạn cần hai thứ: sự ngu dốt và lòng tự tin. - Mark Twain");
        insertMaxim("Không biết đã bao nhiêu lần con người buông tay từ bỏ khi mà chỉ một chút nỗ lực, một chút kiên trì nữa thôi là anh ta sẽ đạt được thành công. - Elbert Hubbard");
        insertMaxim("Thành công và hạnh phúc nằm trong bạn. Quyết tâm hạnh phúc, và niềm vui sẽ đi cùng bạn để hình thành đạo quân bất khả chiến bại chống lại nghịch cảnh. - Helen Keller");
        insertMaxim("Thành công không phải là cuối cùng, thất bại không phải là chết người: lòng can đảm đi tiếp mới quan trọng. - Winston Churchill");
        insertMaxim("Tính cách không thể phát triển một cách dễ dàng và yên lặng. Chỉ qua trải nghiệm thử thách và gian khổ mà tâm hồn trở nên mạnh mẽ hơn, hoài bão hình thành và thành công đạt được. - Helen Keller");
        insertMaxim("Con người chẳng bao giờ lên kế hoạch để thất bại; chỉ đơn giản là họ đã thất bại trong việc lên kế hoạch để thành công. - William Arthur Ward");
        insertMaxim("Để thành công, bạn phải sẵn lòng thất bại. - Frank Tyger");
        insertMaxim("Thành công không phải là chìa khóa mở cánh cửa hạnh phúc. Hạnh phúc là chìa khóa dẫn tới cánh cửa thành công. Nếu bạn yêu điều bạn đang làm, bạn sẽ thành công. - Albert Schweitzer");
        insertMaxim("Đoàn kết, đoàn kết, đại đoàn kết. Thành công, thành công, đại thành công. - Hồ Chí Minh");
        insertMaxim("Tôi tìm thấy lạc thú lớn nhất, và cũng như phần thưởng, nằm trong công việc vượt qua điều mà thế giới gọi là thành công. - Thomas Edison");
        insertMaxim("Xây dựng thành công từ thất bại. Sự chán nản và thất bại là hai bước đệm chắc chắn nhất dẫn tới thành công. - Dale Carnegie");
        insertMaxim("Nấc thang thành công không quan tâm ai đang trèo nó. - Frank Tyger");
        insertMaxim("Những người thành công luôn luôn tìm kiếm cơ hội để giúp đỡ người khác. Những người không thành công luôn luôn hỏi, \"Tôi được lợi gì?\" - Brian Tracy");
        insertMaxim("Thêm một chút bền bỉ, một chút nỗ lực, và điều tưởng chừng như là thất bại vô vọng có thể biến thành thành công rực rỡ. - Elbert Hubbard");
        insertMaxim("Bí quyết của thành công là hãy bắt đầu. Bí quyết để bắt đầu là chia nhỏ các công việc nặng nề, phức tạp thành những việc nhỏ dễ quản lý hơn, rồi bắt đầu với việc thứ nhất. - Mark Twain");
        insertMaxim("Để thành công trong thế giới này, chỉ ngu xuẩn là không đủ, anh cũng phải biết xử thế. - Voltaire");
        insertMaxim("Cơ bản là có hai loại người. Người làm nên chuyện và người tuyên bố mình làm nên chuyện. Nhóm đầu tiên ít đông hơn. - Mark Twain");
        insertMaxim("Chìa khóa thành công là tập trung lý trí của chúng ta vào những điều chúng ta muốn chứ không phải những điều chúng ta sợ. - Brian Tracy");
        insertMaxim("Người ta hiếm khi thành công nếu không làm điều mình thấy vui thích. - Dale Carnegie");
        insertMaxim("Ranh giới giữa thành công và thất bại nhỏ tới mức chúng ta hiếm khi biết được mình đã vượt qua nó khi nào: nhỏ tới mức chúng ta thường đứng trên nó mà không ý thức được. - Elbert Hubbard");
        insertMaxim("Thành công là đạt được thứ bạn muốn. Hạnh phúc là muốn thứ bạn đạt được. - Dale Carnegie");
        insertMaxim("Sự khác biệt giữa những người thành công và những người thất bại không phải ở sức mạnh, kiến thức hay sự hiểu biết - mà chính ở ý chí. - Khuyết danh");
        insertMaxim("Để thành công, thái độ cũng quan trọng ngang bằng khả năng. - Walter Scott");
        insertMaxim("Chúng ta không thể đảm bảo thành công, nhưng chúng ta có thể xứng đáng với thành công. - John Adams");
        insertMaxim("Chọn đúng thời gian, sự bền bỉ và mười năm nỗ lực rồi cuối cùng sẽ khiến bạn có vẻ như thành công chỉ trong một đêm. - Biz Stone");
        insertMaxim("Bạn không cần phải thức đêm để thành công; bạn chỉ cần tỉnh táo vào ban ngày. - Khuyết danh");
        insertMaxim("Chính thái độ của chúng ta khi bắt đầu một việc khó khăn sẽ ảnh hưởng lên kết quả thành công nhiều hơn bất cứ điều gì khác. - William James");
        insertMaxim("Để thành công, hãy chớp lấy cơ hội cũng nhanh như khi vội vã kết luận. - Benjamin Franklin");
        insertMaxim("Bạn được tha thứ cho hạnh phúc và thành công của mình chỉ khi bạn sẵn sàng hào phóng chia sẻ chúng. - Albert Camus");
        insertMaxim("Con người sinh ra để thành công, không phải để thất bại. - Henry David Thoreau");
        insertMaxim("Thất bại chỉ là thành công tạm thời bị trì hoãn, chừng nào lòng can đảm còn tôi luyện cho khát vọng. Thói quen kiên định chính là thói quen chiến thắng. - Herbert Kaufman");
        insertMaxim("Hãy mang cả thành công của người khác trong giấc mơ của mình, vì thành công của chính mình. - Khuyết danh");
        insertMaxim("Để trở thành người thắng cuộc, tất cả những gì bạn cần đến là tất cả những gì bạn có. - Khuyết danh");
        insertMaxim("Cách tốt nhất để thành công trong đời là hành động theo lời khuyên mà ta trao cho người khác. - Khuyết danh");
        insertMaxim("Nếu bạn nhất định phải có được sự công bằng để sống cuộc đời thành công và hạnh phúc, bạn sẽ không làm được điều đó trong kiếp này, trên hành tinh này. - Maxwell Maltz");
        insertMaxim("Sự thành công thường đến với những ai không quá bận rộn đi tìm nó. - Henry David Thoreau");
        insertMaxim("Sự khác biệt giữa người thành công và những người khác không phải là sự thiếu hụt sức mạnh, hay thiếu hụt kiến thức, mà đúng hơn là thiếu hụt ý chí. - Vince Lombardi");
        insertMaxim("Cơ hội thành công của bạn trong mọi chuyện luôn có thể được đo bằng niềm tin của bạn vào chính bản thân mình. - Robert Collier");
        insertMaxim("Thành công không nằm ở việc không bao giờ phạm sai lầm, mà nằm ở việc không bao giờ phạm phải cùng một sai lầm tới lần thứ hai. - George Bernard Shaw");
        insertMaxim("Thành công không phải ngẫu nhiên. Đó là sự chăm chỉ, bền bỉ, học hỏi, nghiên cứu, hy sinh và quan trọng nhất, tình yêu đối với việc mình đang làm. - Pele");
        insertMaxim("Con đường dẫn đến thành công luôn luôn đang được xây dựng. - Khuyết danh");
        insertMaxim("Về cơ bản, có ba loại người: người không thành công, người thành công trong chốc lát, và người thành công bền vững. Sự khác biệt nằm ở nghị lực. - Brian Tracy");
        insertMaxim("Dù tôi không thể cử động, và tôi phải nói qua máy móc, trong trí óc tôi, tôi tự do. - Stephen Hawking");
        insertMaxim("Tự do tối thượng của con người là khả năng quyết định thái độ của mình trong bất cứ hoàn cảnh nào. - Khuyết danh");
        insertMaxim("Khi còn nghèo đói, không có tự do thật sự. - Nelson Mandela");
        insertMaxim("Làm điều bạn thích là tự do. Thích điều bạn làm là hạnh phúc. - Frank Tyger");
        insertMaxim("Luật lệ không bao giờ khiến con người tự do, chính con người phải làm cho luật lệ tự do. - Henry David Thoreau");
        insertMaxim("Càng hiểu biết, con người càng tự do. - Voltaire");
        insertMaxim("Tự do tư tưởng là sức sống của linh hồn. - Voltaire");
        insertMaxim("Sự tự do về chính trị, sự hòa bình của một quốc gia và chính cả khoa học là những món quà mà Định mệnh đánh thuế nặng nề bằng máu! - Balzac");
        insertMaxim("Tôi ưa tự do với nguy hiểm hơn là hòa bình trong nô dịch. - Jean Jacques Rousseau");
        insertMaxim("Tự do không bao giờ là đắt đỏ. Nó là hơi thở của cuộc sống. Có điều gì con người lại không từ bỏ để được sống? - Mahatma Gandhi");
        insertMaxim("Quyền sở hữu chắc chắn cũng là quyền của con người như quyền tự do. - John Adams");
        insertMaxim("Hãy để trí óc con người tự do. Nó phải được tự do. Nó sẽ được tự do. Sự mê tín và giáo điều không thể giam cầm được nó. - John Adams");
        insertMaxim("Kẻ thù của tự do là lãng phí, thờ ơ, phóng đãng, và thái độ xảo quyệt muốn có mà chẳng bỏ công. - William Arthur Ward");
        insertMaxim("Trách nhiệm là cái giá của tự do. - Elbert Hubbard");
        insertMaxim("Không thể có tự do thật sự mà không có tự do vấp ngã. - Erich Fromm");
        insertMaxim("Nên giáo dục và hướng dẫn trẻ em những nguyên tắc của tự do. - John Adams");
        insertMaxim("Không có gì quý hơn độc lập, tự do! - Hồ Chí Minh");
        insertMaxim("Lịch sử chỉ có thể được viết tốt ở một đất nước tự do. - Voltaire");
        insertMaxim("Tự do không đáng để có nếu nó không bao gồm tự do được sai lầm. - Mahatma Gandhi");
        insertMaxim("Cách bảo vệ tự do là đặt nó vào tay nhân dân, cũng có nghĩa là cho họ sức mạnh trong mọi lúc để bảo vệ nó một cách hợp pháp và trước các tòa án công lý. - John Adams");
        insertMaxim("Tự do phục tùng luật lệ mà một người tự thiết lập cho chính mình. - Jean Jacques Rousseau");
        insertMaxim("Tự do không là gì khác ngoài cơ hội trở nên tốt đẹp hơn. - Albert Camus");
        insertMaxim("Mong sao chúng ta nghĩ về tự do không phải như quyền làm bất cứ điều gì mình muốn, mà là cơ hội làm điều đúng đắn. - Peter Marshall");
        insertMaxim("Có sự nhiệt huyết rõ ràng đối với tự do, khiến bản tính con người vượt lên trên chính nó, trong những hành động can đảm và anh hùng. - Alexander Hamilton");
        insertMaxim("Con người trở nên tự do khi anh ta nhận ra mình bị luật lệ ràng buộc. - Will Durant");
        insertMaxim("Tự do, theo quan điểm siêu hình học của tôi, là quyền lực tự quyết trong một thực thể có tư duy. Nó đưa đến suy nghĩ, lựa chọn và sức mạnh. - John Adams");
        insertMaxim("Tìm kiếm tự do và trở thành tù nhân của dục vọng. Tìm kiếm kỷ luật và tìm thấy tự do. - Frank Herbert");
        insertMaxim("Lịch sử của thế giới chính là tiến trình của ý thức tự do. - Hegel");
        insertMaxim("Những người có thể từ bỏ tự do trân quý để đổi lấy sự an toàn tạm thời ít ỏi không xứng đáng với cả tự do lẫn sự an toàn. - Benjamin Franklin");
        insertMaxim("Người vượt qua được nỗi sợ của mình sẽ thực sự có tự do. - Aristotle");
        insertMaxim("Sự cần thiết mù cho tới khi nó trở thành ý thức. Tự do là ý thức về sự cần thiết. - Karl Marx");
        insertMaxim("Con người tự do vào lúc muốn mình tự do. - Voltaire");
        insertMaxim("Mỗi chúng ta đều xứng đáng có được tự do để theo đuổi cách hạnh phúc của riêng mình. Không ai đáng phải chịu ức hiếp. - Barack Obama");
        insertMaxim("Tôi chỉ muốn được tự do. Bướm rất tự do. - Charles Dickens");
        insertMaxim("Thông tin không phải là kiến thức. - Albert Einstein");
        insertMaxim("Kẻ ngu dốt nghĩ mình thông thái, nhưng người thông thái biết mình ngu dốt. - Anatole France");
        insertMaxim("Bí mật của kinh doanh là biết được điều gì đó mà không ai khác biết. - Aristotle Onassis");
        insertMaxim("Đây là sự thật lớn nhất của thời đại chúng ta: Thông tin không phải là tri thức. - Caleb Carr");
        insertMaxim("Trẻ con là những kẻ bắt chước bẩm sinh hành động giống như cha mẹ mình bất chấp mọi nỗ lực để dạy chúng cách xử thế. - Khuyết danh");
        insertMaxim("Di sản tốt nhất mà cha mẹ có thể dành cho con cái là chút ít thời gian mỗi ngày. - Orlando Aloysius Battista");
        insertMaxim("Thờ cha mẹ, nên nhỏ nhẹ khuyên can, nếu thấy cha mẹ không theo ý mình thì vẫn cung kính mà không xúc phạm cha mẹ; tuy khó nhọc, lo buồn, nhưng không được oán hận. - Khổng Tử");
        insertMaxim("Còn điều gì tốt đẹp hơn mà cha mẹ và con cái có thể trao cho nhau ngoài sự quan tâm tôn trọng và thấu hiểu. - Richard L Evans");
        insertMaxim("Gia đình là gì? Đó là tình mến sợ cha, tình yêu mến mẹ, kính trọng thán phục nhân đức của cả cha lẫn mẹ, bỏ qua lỗi lầm, ghi nhớ công ơn, thông cảm nỗi đau khổ, cảm kích sự hy sinh của cha mẹ. - Khuyết danh");
        insertMaxim("Một ngày mới: Hãy đủ cởi mở để nhìn thấy cơ hội. Hãy đủ khôn ngoan để cảm thấy biết ơn. Hãy đủ can đảm để hạnh phúc. - Steve Maraboli");
        insertMaxim("Khi một cánh cửa đóng lại, cánh cửa khác mở ra; nhưng chúng ta thường nuối tiếc nhìn mãi cánh cửa đóng đến nỗi ta không thấy cánh cửa đã mở ra. - Alexander Graham Bell");
        insertMaxim("Tương lai có rất nhiều tên: Với kẻ yếu, nó là Điều không thể đạt được. Đối với người hay sợ hãi, nó là Điều chưa biết. Với ai dũng cảm, nó là Cơ hội. - Victor Hugo");
        insertMaxim("Mọi thứ tiêu cực - áp lực, thử thách - đều là cơ hội để tôi vươn lên. - Kobe Bryant");
        insertMaxim("Hãy nắm lấy cơ hội! Tất cả cuộc đời là cơ hội. Người tiến xa nhất thường là người sẵn sàng hành động và chấp nhận thách thức. - Dale Carnegie");
        insertMaxim("Bạn càng tìm kiếm sự bảo đảm, bạn càng ít có nó. Nhưng bạn càng tìm kiếm cơ hội, bạn càng có thể đạt được sự bảo đảm mà mình muốn. - Brian Tracy");
        insertMaxim("Thời gian vội vã lao đi; Cơ hội nảy sinh rồi tan biến... Vậy mà bạn vẫn chờ đợi và không dám thử - Con chim có đôi cánh mà không dám bay lên. - A. A. Milne");
        insertMaxim("Để thành công, hãy chớp lấy cơ hội cũng nhanh như khi vội vã kết luận. - Benjamin Franklin");
        insertMaxim("Tôi hiếm khi nhận ra được cơ hội cho tới khi mà nó không còn là cơ hội. - Mark Twain");
        insertMaxim("Hãy học cách lắng nghe. Cơ hội có thể gõ cửa rất khẽ khàng. - Frank Tyger");
        insertMaxim("Anh muốn bỏ phần còn lại của cuộc đời mình đi bán nước đường hay anh muốn có cơ hội thay đổi thế giới? - Câu hỏi nổi tiếng của Steve Jobs dành cho John Sculley, giám đốc trước của Apple. - Steve Jobs");
        insertMaxim("Thử thách càng lớn, cơ hội càng lớn. - Khuyết danh");
        insertMaxim("Cơ hội thành công của bạn trong mọi chuyện luôn có thể được đo bằng niềm tin của bạn vào chính bản thân mình. - Robert Collier");
        insertMaxim("Người bi quan luôn tìm thấy khó khăn trong mọi cơ hội. Người lạc quan luôn nhìn được cơ hội trong từng khó khăn. - Khuyết danh");
        insertMaxim("Hãy tập trung nỗ lực cao nhất vào những cơ hội tốt nhất chứ không phải những rắc rối tồi tệ nhất. - Khuyết danh");
        insertMaxim("Trên trái đất này không có sự bảo đảm; chỉ có cơ hội. - Douglas MacArthur");
        insertMaxim("Nếu bạn không bỏ cuộc, bạn vẫn còn cơ hội. Bỏ cuộc là thất bại lớn nhất. - Jack Ma");
        insertMaxim("Cơ hội giống như bình minh. Nếu bạn chờ quá lâu, bạn sẽ bỏ lỡ nó. - William Arthur Ward");
        insertMaxim("Cơ hội đến với tất cả mọi người, nhưng ít người có thể nắm bắt được cơ hội. - Edward Bulwer Lytton");
        insertMaxim("Anh ta chưa bao giờ thành công lắm. Khi cơ hội gõ cửa, anh ta phàn nàn về tiếng ồn. - Khuyết danh");
        insertMaxim("Nằm giữa khó khăn là cơ hội. - Albert Einstein");
        insertMaxim("Người lạc quan luôn nhìn thấy cơ hội trong mọi hiểm nguy, còn kẻ bi quan luôn nhìn thấy hiểm nguy trong mọi cơ hội. - Winston Churchill");
        insertMaxim("Ngay cả khi trong túi hết tiền, cái mũ trên đầu anh cũng phải đội cho ngay ngắn. - Ngạn ngữ Tây Ban Nha");
        insertMaxim("Cần, Kiệm, Liêm, là gốc rễ của Chính. Nhưng một cây cần có gốc rễ, lại cần có cành, lá, hoa, quả mới là hoàn toàn. Một người phải Cần, Kiệm, Liêm, nhưng còn phải Chính mới là người hoàn toàn. - Hồ Chí Minh");
        insertMaxim("Những người trong các công sở đều có nhiều hoặc ít quyền hành. Nếu không giữ đúng Cần - Kiệm - Liêm - Chính thì dễ trở thành hủ bại, biến thành sâu mọt của dân. - Hồ Chí Minh");
        insertMaxim("Tự mình phải chính trước, mới giúp được người khác chính. Mình không chính, mà muốn người khác chính là vô lý. - Hồ Chí Minh");
        insertMaxim("Lương thiện là đồng tiền duy nhất có thể đi khắp thế giới. - Ngạn ngữ Pháp");
        insertMaxim("Tài năng, kỹ năng hay tài hùng biện, dù nhiều bao nhiêu cũng không thể chống đỡ ta như sự chính trực, chí khí và thái độ. - Sidney Mohede");
        insertMaxim("Hãy can đảm nói không. Hãy can đảm đối mặt với sự thật. Hãy làm điều đúng vì như thế là đúng. Đó là những chiếc chìa khóa kỳ diệu mở cánh cửa vào cuộc đời chính trực. - W. Clement Stone");
        insertMaxim("Phú quý lòng hơn phú quý danh. - Nguyễn Trãi");
        insertMaxim("Công trạng của cá nhân chủ yếu là nhờ tập thể mà có. Vì vậy người có công trạng không nên tự kiêu mà cần khiêm tốn. Khiêm tốn và rộng lượng, đó là hai đức tính mà người cách mạng nào cũng phải có. - Hồ Chí Minh");
        insertMaxim("Người tầm thường không tin vào các cá nhân vĩ đại. - Jean Jacques Rousseau");
        insertMaxim("Việc đọc rất quan trọng. Nếu bạn biết cách đọc, cả thế giới sẽ mở ra cho bạn. - Barack Obama");
        insertMaxim("Bạn không thể để thất bại định hình mình. Bạn phải để thất bại dạy mình. - Barack Obama");
        insertMaxim("Thay đổi chẳng bao giờ dễ, nhưng luôn luôn có thể. - Barack Obama");
        insertMaxim("Tương lai trao thưởng cho những ai tiến lên phía trước. Tôi không có thời gian để cảm thấy tiếc nuối cho mình. Tôi không có thời gian để phàn nàn. Tôi sẽ tiến về phía trước. - Barack Obama");
        insertMaxim("Tập trung cả đời vào việc kiếm tiền cho thấy sự nghèo nàn về tham vọng. Bạn yêu cầu quá ít ở bản thân. Và điều đó sẽ khiến bạn không thỏa mãn. - Barack Obama");
        insertMaxim("Khi còn thở, ta còn hy vọng. - Barack Obama");
        insertMaxim("Thay đổi xuất hiện nhờ những người bình thường làm những chuyện phi thường. - Barack Obama");
    }

    public String getMaxim() {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 1;";
        String maxim = null;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            maxim = cursor.getString(1);
        }

        cursor.close();
        db.close();
        return maxim;
    }

    public int count(){
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = 0;
        try {
            if (cursor.moveToFirst()) {
                count = cursor.getCount();
            }
        }
        finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return count;
    }
}
